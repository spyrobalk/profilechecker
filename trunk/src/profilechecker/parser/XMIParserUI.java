package profilechecker.parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import profilechecker.Profile;
import profilechecker.Stereotype;

public class XMIParserUI {

	final static String LINE_SEPARATOR = System.getProperty("line.separator");

	public String parse(File file) throws ParserConfigurationException, SAXException, IOException {
		XMIParser parser = new XMIParser(file);
		Map<String, Profile> profiles = parser.parse();
		StringBuilder sb = new StringBuilder();
		for (String profileName : profiles.keySet()) {
			Profile profile = profiles.get(profileName);
			sb.append("Profile").append(LINE_SEPARATOR);
			sb.append("   name       : " + profile.getName()).append(
					LINE_SEPARATOR);
			sb.append("   id         : " + profile.getId()).append(
					LINE_SEPARATOR);
			sb.append("   visibility : " + profile.getVisibility()).append(
					LINE_SEPARATOR);
			sb.append(LINE_SEPARATOR);
			Map<String, Stereotype> stereotypes = profiles.get(profileName)
					.getStereotypes();
			for (String stereotypeName : stereotypes.keySet()) {
				Stereotype stereotype = stereotypes.get(stereotypeName);
				sb.append("   Stereotype").append(LINE_SEPARATOR);
				sb.append("      name       : " + stereotype.getName()).append(
						LINE_SEPARATOR);
				sb.append("      id         : " + stereotype.getId()).append(
						LINE_SEPARATOR);
				sb.append("      visibility : " + stereotype.getVisibility())
						.append(LINE_SEPARATOR);
				for (String type : stereotype.getTypes()) {
					sb.append("      type       : " + type).append(
							LINE_SEPARATOR);
				}
				sb.append(LINE_SEPARATOR);
			} // End of 'stereotypes for-each'
			sb.append(LINE_SEPARATOR);
		} // End of 'profiles for-each'
		sb.append(LINE_SEPARATOR);
		return sb.toString();
	}
}
