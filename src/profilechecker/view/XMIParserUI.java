package profilechecker.view;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.xml.sax.SAXException;

import profilechecker.controller.ProfileCheckerController;
import profilechecker.model.Member;
import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.Stereotype;

/**
 * UI of the XMIParser. And should be used as a debug tool from command line.
 * 
 * @author Matheus
 */
public class XMIParserUI {

	/** Line separator to be used by the UI. */
	final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
	/** Application model. */
	private Model model;
	
	/** Application controller. */
	private ProfileCheckerController controller;

	/**
	 * Default XMIParserUI constructor.
	 * @param controller Application controller.
	 * @param model Application model.
	 */
	public XMIParserUI(ProfileCheckerController controller, Model model) {
		this.controller = controller;
		this.model = model;
	}
	
	/**
	 * Parse a XMI file.
	 * 
	 * @param file
	 *            File to be parsed.
	 * @return A String with profile information to be printed.
	 * @throws SAXException
	 *             If the parse fails.
	 * @throws IOException
	 *             It its not possible to read the file.
	 */
	public String parse(File file) throws
			SAXException, IOException {
		controller.parser(model, file);
		Map<String, Profile> profiles = model.getProfiles(); 
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
		
		Map<String, Package> packages = model.getPackages();
		for(String packageId: packages.keySet()){
			Package currentPackage = packages.get(packageId);
			sb.append("Package").append(LINE_SEPARATOR);
			sb.append("   name       : " + currentPackage.getName()).append(
					LINE_SEPARATOR);
			sb.append("   id         : " + currentPackage.getId()).append(
					LINE_SEPARATOR);
			sb.append("   visibility : " + currentPackage.getVisibility())
					.append(LINE_SEPARATOR);
			Map<String,Profile> packageProfiles = currentPackage.getProfiles();
			for (String profileId : packageProfiles.keySet()) {
				sb.append("   Profile").append(LINE_SEPARATOR);
				sb.append("      name       : " + packageProfiles.get(profileId).getName()).append(
						LINE_SEPARATOR);
			}
			Map<String,Member> packageMembers = currentPackage.getMembers();
			for (String classId : packageMembers.keySet()) {
				sb.append("   Class").append(LINE_SEPARATOR);
				sb.append("      id         : " + packageMembers.get(classId).getId()).append(
						LINE_SEPARATOR);
				sb.append("      name       : " + packageMembers.get(classId).getName()).append(
						LINE_SEPARATOR);
				sb.append("      visibility : " + packageMembers.get(classId).getVisibility()).append(
						LINE_SEPARATOR);

				// TODO get this information from StereotypeApplication
				
//				Set<StereotypeApplication> applications = model.getApplications();
//				Map<String, Stereotype> stereotypes = packageMembers.get(classId).getStereotypes();
//				for (String stereotypeName : stereotypes.keySet()) {
//					Stereotype stereotype = stereotypes.get(stereotypeName);
//					sb.append("         Stereotype").append(LINE_SEPARATOR);
//					sb.append("            name       : " + stereotype.getName()).append(
//							LINE_SEPARATOR);
//				}
			}
			
			sb.append(LINE_SEPARATOR);
		}
		sb.append(LINE_SEPARATOR);
		return sb.toString();
	}
}
