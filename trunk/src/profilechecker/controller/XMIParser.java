package profilechecker.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import profilechecker.model.Member;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.StereotypeApplication;
import profilechecker.model.VisibilityType;

/**
 * Parser of MagicDraw XMI files. It will parse a XMI file and create the
 * profile structure (with stereotypes) that this XMI contains. It uses a
 * SAXParser since the most important task here is to validate and obtain our
 * own profiles objects.
 * 
 * Also notice that a SAX parser is more efficient than a DOM parser.
 * 
 * @author Matheus
 */
class XMIParser extends DefaultHandler {

	/** Map to hold the profiles of this XMI. The key is the profile ID. */
	private Map<String, Profile> profiles;
	
	/** Map to hold the packages of this XMI. The key is the package ID. */
	private Map<String, Package> packages;
	
	/** Set to hold the stereotype applications of this XMI. */
	private Set<StereotypeApplication> applications;

	/** SAXParser to be used. */
	private SAXParser sparser;

	/** Current parsing profile. */
	private Profile parsingProfile;

	/** Current parsing stereotype. */
	private Stereotype parsingStereotype;
	
	/** Current parsing package. */
	private Package parsingPackage;
	
	/** Current parsing class. */
	private Member parsingMember;

	/**
	 * Counts the level of OwnedMember so we can know when a stereotype or a
	 * profile ends.
	 */
	private int ownedMemberCount;

	/** OwnedMember level of current parsing stereotype. */
	private int ownedMemberStereotypeCount;

	/** OwnedMember level of current parsing profile. */
	private int ownedMemberProfileCount;

	/** OwnedMember level of current parsing package */
	private int ownedMemberPackageCount;
	
	/** OwnedMember level of current parsing package */
	private int ownedMemberMemberCount;
	
	/**
	 * Boolean to control if we are parsing an associated type of the current
	 * parsing stereotype.
	 */
	private boolean isParsingType;
	
	/**
	 * Boolean to control if we are parsing classes or profile association 
	 * to a package. 
	 */
	private boolean isParsingPackage;
	
	/**
	 * Integer to control how deep we are at the XMI.
	 */
	private int xmiDeep;

	/** Deep of xmi:extension. Ignore anyone outside the profile package. */
	private int xmiExtensionDeep;


	/**
	 * Creates a XMI Parser.
	 * 
	 * @throws ParserConfigurationException
	 *             If its not possible to create a parser.
	 * @throws SAXException
	 *             If the parser fails.
	 */
	XMIParser() throws ParserConfigurationException,
			SAXException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		sparser = spf.newSAXParser();
		reset();
	}

	/**
	 * Parse the file.
	 * 
	 * @param file File to be parsed.
	 * 
	 * @return Model with read profiles, packages and stereotype applications. 
	 * 
	 * @throws SAXException
	 *             If it fails to parse the file.
	 * @throws IOException
	 *             If there is an IOException while reading the file.
	 */
	Model parse(File file) throws SAXException, IOException {
		
		if (!file.exists()) {
			throw new IOException("File not found: " + file.getPath());
		}

		this.reset();
		this.sparser.parse(file, this);
		return new Model(profiles, packages, applications);
	}

	/**
	 * Reset all fields to allow a new parse.
	 */
	private void reset() {
		this.ownedMemberCount = 0;
		this.ownedMemberStereotypeCount = -1;
		this.ownedMemberProfileCount = -1;
		this.ownedMemberPackageCount = -1;
		this.ownedMemberMemberCount = -1;
		this.isParsingType = false;
		this.isParsingPackage = false;
		this.xmiDeep = 0;
		this.xmiExtensionDeep = -1;
		this.profiles = new HashMap<String, Profile>();
		this.packages = new HashMap<String, Package>();
		this.applications = new LinkedHashSet<StereotypeApplication>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		xmiDeep++;
		
		if (xmiExtensionDeep != -1
				|| (ownedMemberProfileCount == -1 && "xmi:Extension"
						.equalsIgnoreCase(qName))) {
			xmiExtensionDeep++;
			return;
		}
		if ("ownedMember".equalsIgnoreCase(qName)) {
			ownedMemberCount++;
			if ("uml:Profile".equals(attributes.getValue("xmi:type"))) {
				parsingProfile = new Profile(attributes.getValue("name"),
						attributes.getValue("xmi:id"), VisibilityType
								.toValue(attributes.getValue("visibility")));
				ownedMemberProfileCount = ownedMemberCount;
			} else if ("uml:Stereotype".equals(attributes.getValue("xmi:type"))) {
				if (parsingProfile == null) {
					// TODO ERROR parsing a stereotype outside a profile.
				}
				parsingStereotype = new Stereotype(attributes.getValue("name"),
						attributes.getValue("xmi:id"), VisibilityType
								.toValue(attributes.getValue("visibility")));
				ownedMemberStereotypeCount = ownedMemberCount;
			} else if ("uml:Package".equals(attributes.getValue("xmi:type"))) {
				
				//TODO check this magic condition
				if(attributes.getValue("href") == null){
					
					parsingPackage = new Package(attributes.getValue("name"),
							attributes.getValue("xmi:id"), VisibilityType.
							toValue(attributes.getValue("visibility")));
				
					ownedMemberPackageCount = ownedMemberCount;
				}
			} else if (isParsingPackage) { // TODO is that it?
				
				
				parsingMember = new Member(attributes.getValue("name"),
						attributes.getValue("xmi:id"), VisibilityType.
						toValue(attributes.getValue("visibility")),
						attributes.getValue("xmi:type"));
				ownedMemberMemberCount = ownedMemberCount;
			}

		}
		
		if (xmiDeep == 2) {
			StringTokenizer tokens = new StringTokenizer(qName,":");
			
			String profileName = tokens.nextToken();
			String stereotypeName = tokens.nextToken();
			String baseName = "";
			for (int i = 0; i < attributes.getLength(); i++) {
				String attributeName = attributes.getQName(i);
			
				if(attributeName.startsWith("base_")){
					baseName = attributeName.substring(5);
				}
				// TODO VERIFY IF THERE IS ONLY ONE BASE_*
			}
			
			if (! "".equals(baseName)) {
				StereotypeApplication stereotypeApp = new StereotypeApplication(attributes.getValue("xmi:id"),
						baseName,attributes.getValue("base_"+baseName),stereotypeName,profileName);
				applications.add(stereotypeApp);		
			}
		}
		
		if(isParsingPackage && "packageImport".equalsIgnoreCase(qName)){
			if ("uml:ProfileApplication".equals(attributes.getValue("xmi:type"))){
				// TODO what if the key doesnt exist
				String profileId = attributes.getValue("importedProfile");
				parsingPackage.addProfile(profileId,profiles.get(profileId));
			}
		}
		
		if(ownedMemberPackageCount == ownedMemberCount){// TODO just this condition?
			isParsingPackage = true;
		}

		// An referenceExtension from stereotype TODO It is ok?
		if ("ownedAttribute".equalsIgnoreCase(qName)
				&& ownedMemberStereotypeCount == ownedMemberCount) {
			isParsingType = true;
		}
		


		if (isParsingType && "referenceExtension".equalsIgnoreCase(qName)) {
			parsingStereotype.addType(attributes.getValue("referentPath"));
			isParsingType = false;
		}

	}

	@Override
	public void error(SAXParseException e) {
		// TODO Something is wrong...
	}

	@Override
	public void fatalError(SAXParseException e) {
		// TODO Something is VERY wrong...
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		xmiDeep--;
		
		if (xmiExtensionDeep != -1
				|| (ownedMemberProfileCount == -1 && "xmi:Extension"
						.equalsIgnoreCase(qName))) {
			xmiExtensionDeep--;
			return; // Ignoring
		}
		
		if (qName.equalsIgnoreCase("ownedMember")) {
			if (ownedMemberProfileCount == ownedMemberCount) {
				profiles.put(parsingProfile.getId(), parsingProfile);
				ownedMemberProfileCount = -1;
				parsingProfile = null;
			} else if (ownedMemberStereotypeCount == ownedMemberCount) {
				parsingProfile.addStereotype(parsingStereotype.getId(),
						parsingStereotype);
				ownedMemberStereotypeCount = -1;
				parsingStereotype = null;
			} else if (ownedMemberPackageCount == ownedMemberCount ){ // TODO cant I merge this to ifs into one 
				packages.put(parsingPackage.getId(), parsingPackage);
				ownedMemberPackageCount = -1;
				parsingPackage = null;
				isParsingPackage = false;
			}else if (ownedMemberMemberCount == ownedMemberCount){
				if(parsingPackage != null ){
					parsingPackage.addMember(parsingMember.getId(), parsingMember);
				} else {
					// TODO what to do with a class without a package
					// this situation even exists or not
				}
				
				ownedMemberMemberCount = -1;
				parsingMember = null;
				
			}
			ownedMemberCount--;
		}

	}

}