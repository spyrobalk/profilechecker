package profilechecker.parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import profilechecker.Profile;
import profilechecker.Stereotype;
import profilechecker.VisibilityType;

public class XMIParser extends DefaultHandler {

	private Map<String, Profile> profiles;
	private SAXParser sparser;
	
	private Profile parsingProfile;
	private Stereotype parsingStereotype;

	private int ownedMemberCount = 0;
	private int ownedMemberStereotypeCount = -1;
	private int ownedMemberProfileCount = -1;

	private int xmiExtensionDeep = -1;
	private File file;

	public XMIParser(File file) throws ParserConfigurationException, SAXException, IOException {

		if (!file.exists()) {
			throw new IOException("File not found: " + file.getName());
		}
		
		this.file = file;
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		sparser = spf.newSAXParser();
		this.profiles = new HashMap<String, Profile>();
	}

	public Map<String, Profile> parse() throws SAXException, IOException {
		sparser.parse(file, this);
		return this.profiles;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ( xmiExtensionDeep != -1 || (ownedMemberProfileCount == -1 && "xmi:Extension".equalsIgnoreCase(qName))) {
			xmiExtensionDeep++;
			return;
		}
		if (qName.equalsIgnoreCase("ownedMember")) {
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
			}

		}
		if (qName.equalsIgnoreCase("ownedAttribute")) {
			// TODO Implement this...
		}

	}

	@Override
	public void error (SAXParseException e) {
		// TODO Something is wrong...
	}
	
	@Override
	public void fatalError (SAXParseException e) {
		// TODO Something is VERY wrong...
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (xmiExtensionDeep != -1 || (ownedMemberProfileCount == -1 && "xmi:Extension".equalsIgnoreCase(qName))) {
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
			}
			ownedMemberCount--;
		}

		if (qName.equalsIgnoreCase("ownedAttribute")) {
			// TODO Implement this...
		}

	}

	

}