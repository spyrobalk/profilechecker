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
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import profilechecker.model.Member;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;
import profilechecker.model.StereotypeApplication;
import profilechecker.model.ValidationException;
import profilechecker.model.VisibilityType;
import profilechecker.model.ValidationException.Level;

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

	/** Current validation exceptions. */
	private Set<ValidationException> validationExceptions;
	
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

	/** Locator to get current line. */
    private Locator locator;

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }
	

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
		Model model = new Model(profiles, packages, applications);
		model.setValidationExceptions( this.validationExceptions );
		return model;
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
		this.validationExceptions = new LinkedHashSet<ValidationException>();
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
		
		int line = -1;
		
		if (locator != null) {
			line = locator.getLineNumber();
		}
		
		if ("ownedMember".equalsIgnoreCase(qName)) {
			ownedMemberCount++;
			if ("uml:Profile".equals(attributes.getValue("xmi:type"))) {
				String parsingProfileName = attributes.getValue("name");
				if (parsingProfileName == null) {
					validationExceptions.add( new ValidationException("This profile will not be appliable without a name.", Level.warning, line) );
				}
				parsingProfile = new Profile(parsingProfileName,
						attributes.getValue("xmi:id"), VisibilityType
								.toValue(attributes.getValue("visibility")), line);
				ownedMemberProfileCount = ownedMemberCount;
			} else if ("uml:Stereotype".equals(attributes.getValue("xmi:type"))) {
				if (parsingProfile == null) {
					validationExceptions.add( new ValidationException("Stereotype must be part of a profile.", Level.fatal, line) );
				}
				String stereotypeName = attributes.getValue("name");
				if (stereotypeName == null) {
					validationExceptions.add( new ValidationException("This stereotype will not be appliable without a name.", Level.warning, line) );
				}
				parsingStereotype = new Stereotype(stereotypeName,
						attributes.getValue("xmi:id"), VisibilityType
								.toValue(attributes.getValue("visibility")), line);
				ownedMemberStereotypeCount = ownedMemberCount;
			} else if ("uml:Package".equals(attributes.getValue("xmi:type"))) {
				
				//TODO check this magic condition
				if(attributes.getValue("href") == null){
					
					parsingPackage = new Package(attributes.getValue("name"),
							attributes.getValue("xmi:id"), VisibilityType.
							toValue(attributes.getValue("visibility")), line);
				
					ownedMemberPackageCount = ownedMemberCount;
				}
			} else if (isParsingPackage) { // TODO is that it?
				String type = attributes.getValue("xmi:type");
				String[] names = type.split(":");
				parsingMember = new Member(attributes.getValue("name"),
						attributes.getValue("xmi:id"), VisibilityType.
						toValue(attributes.getValue("visibility")),
						names[names.length-1], line);
				ownedMemberMemberCount = ownedMemberCount;
			}

		}
		
		if (xmiDeep == 2) {
			StringTokenizer tokens = new StringTokenizer(qName,":");
			
			String profileName = tokens.nextToken();
			String stereotypeName = tokens.nextToken();

			// Ignoring MagicDraw profile
			if (! "MagicDraw_Profile".equals( profileName.trim() ))  {
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
							baseName,attributes.getValue("base_"+baseName),stereotypeName,profileName, line);
					applications.add(stereotypeApp);		
				}
			}
		}
		
		if(isParsingPackage && "packageImport".equalsIgnoreCase(qName)){
			if ("uml:ProfileApplication".equals(attributes.getValue("xmi:type"))){
				// TODO what if the key doesnt exist
				String profileId = attributes.getValue("importedProfile");
				for (Profile profile : profiles.values()) {
					if (profile.getId().equals( profileId )) {
						parsingPackage.addProfile( profile.getName(), profile);
					}
				}
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
			String[] names = attributes.getValue("referentPath").split("::");
			parsingStereotype.addType(names[names.length-1]);
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
				profiles.put(parsingProfile.getName(), parsingProfile);
				ownedMemberProfileCount = -1;
				parsingProfile = null;
			} else if (ownedMemberStereotypeCount == ownedMemberCount) {
				parsingProfile.addStereotype(parsingStereotype.getName(),
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