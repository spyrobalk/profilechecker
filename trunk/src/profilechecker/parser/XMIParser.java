package profilechecker.parser;

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

import profilechecker.Member;
import profilechecker.Package;
import profilechecker.Profile;
import profilechecker.Stereotype;
import profilechecker.StereotypeApplication;
import profilechecker.VisibilityType;

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
public class XMIParser extends DefaultHandler {

	private static final String XMI_XMI = "xmi:XMI";

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
	private int ownedMemberCount = 0;

	/** OwnedMember level of current parsing stereotype. */
	private int ownedMemberStereotypeCount = -1;

	/** OwnedMember level of current parsing profile. */
	private int ownedMemberProfileCount = -1;

	/** OwnedMember level of current parsing package */
	private int ownedMemberPackageCount = -1;
	
	/** OwnedMember level of current parsing package */
	private int ownedMemberMemberCount = -1;
	
	/**
	 * Boolean to control if we are parsing an associated type of the current
	 * parsing stereotype.
	 */
	private boolean isParsingType = false;
	
	/**
	 * Boolean to control if we are parsing classes or profile association 
	 * to a package. 
	 */
	private boolean isParsingPackage = false;
	
	/**
	 * Integer to control how deep we are at the XMI.
	 */
	private int xmiDeep = 0;

	/** Deep of xmi:extension. Ignore anyone outside the profile package. */
	private int xmiExtensionDeep = -1;

	/** File to be parsed. */
	private File file;

	

	/**
	 * Creates a XMI Parser.
	 * 
	 * @param file
	 *            XMI file to be parsed.
	 * @throws ParserConfigurationException
	 *             If its not possible to create a parser.
	 * @throws SAXException
	 *             If the parser fails.
	 * @throws IOException
	 *             If its not possible to read the file.
	 */
	public XMIParser(File file) throws ParserConfigurationException,
			SAXException, IOException {

		if (!file.exists()) {
			throw new IOException("File not found: " + file.getPath());
		}

		this.file = file;

		SAXParserFactory spf = SAXParserFactory.newInstance();
		sparser = spf.newSAXParser();
		this.profiles = new HashMap<String, Profile>();
		this.packages = new HashMap<String, Package>();
		this.applications = new LinkedHashSet<StereotypeApplication>();
	}

	/**
	 * Parse the file.
	 * 
	 * @return Map with parsed profiles.
	 * @throws SAXException
	 *             If it fails to parse the file.
	 * @throws IOException
	 *             If there is an IOException while readint the file.
	 */
	public void parse() throws SAXException, IOException {
		sparser.parse(file, this);
	}
	
	public Map<String, Profile> getProfiles(){
		return this.profiles;
	}
	
	public Map<String, Package> getPackages(){
		return this.packages;
	}
	
	public Set<StereotypeApplication> getApplications(){
		return this.applications;
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
		
		if((xmiDeep == 1) && ownedMemberCount == 0){
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
					parsingPackage.addClass(parsingMember.getId(), parsingMember);
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