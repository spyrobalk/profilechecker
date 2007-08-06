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

	/** Map to hold the profiles of this XMI. The key is the profile ID. */
	private Map<String, Profile> profiles;

	/** SAXParser to be used. */
	private SAXParser sparser;

	/** Current parsing profile. */
	private Profile parsingProfile;

	/** Current parsing stereotype. */
	private Stereotype parsingStereotype;

	/**
	 * Counts the level of OwnedMember so we can know when a stereotype or a
	 * profile ends.
	 */
	private int ownedMemberCount = 0;

	/** OwnedMember level of current parsing stereotype. */
	private int ownedMemberStereotypeCount = -1;

	/** OwnedMember level of current parsing profile. */
	private int ownedMemberProfileCount = -1;

	/**
	 * Boolean to control if we are parsing a associated type of the current
	 * parsing stereotype.
	 */
	private boolean isParsingType = false;

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
			throw new IOException("File not found: " + file.getName());
		}

		this.file = file;

		SAXParserFactory spf = SAXParserFactory.newInstance();
		sparser = spf.newSAXParser();
		this.profiles = new HashMap<String, Profile>();
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
	public Map<String, Profile> parse() throws SAXException, IOException {
		sparser.parse(file, this);
		return this.profiles;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
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
			}

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
			}
			ownedMemberCount--;
		}

	}

}