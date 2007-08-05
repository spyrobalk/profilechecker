package profilechecker.parser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

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

	private Set<Profile> profiles;
	private SAXParser sparser;
	
	private Profile parsingProfile;
	private Stereotype parsingStereotype;

	private int ownedMemberCount = 0;
	private int ownedMemberStereotypeCount = -1;
	private int ownedMemberProfileCount = -1;

	public XMIParser(File file) throws ParserConfigurationException, SAXException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		sparser = spf.newSAXParser();
		this.profiles = new LinkedHashSet<Profile>();
	}

	public Set<Profile> parse(File file) throws SAXException, IOException {
		sparser.parse(file, this);
		return this.profiles;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
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

		if (qName.equalsIgnoreCase("ownedMember")) {
			ownedMemberCount--;
			if (ownedMemberProfileCount == ownedMemberCount) {
				profiles.add(parsingProfile);
				ownedMemberProfileCount = -1;
				parsingProfile = null;
			} else if (ownedMemberStereotypeCount == ownedMemberCount) {
				parsingProfile.addStereotype(parsingStereotype.getName(),
						parsingStereotype);
				ownedMemberStereotypeCount = -1;
				parsingStereotype = null;
			}
		}

		if (qName.equalsIgnoreCase("ownedAttribute")) {
			// TODO Implement this...
		}

	}

	

}