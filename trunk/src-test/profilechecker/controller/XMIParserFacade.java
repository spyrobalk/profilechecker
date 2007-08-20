package profilechecker.controller;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import profilechecker.model.Model;
import profilechecker.model.Package;
import profilechecker.model.Profile;
import profilechecker.model.StereotypeApplication;

/**
 * EasyAccept facade to use the XMIParser.
 * 
 * @author Matheus
 */
public class XMIParserFacade {

	/** Map of parsed profiles. */
	private Map<String, Profile> profiles;
	private Map<String, Package> packages;
	private Set<StereotypeApplication> applications;

	/**
	 * Creates the facade.
	 */
	public XMIParserFacade() {
		reset();
	}

	/**
	 * Parse a XMI file and save the profiles in this object.
	 * 
	 * @param file
	 *            File to be parsed.
	 * @throws Exception
	 *             If something fails.
	 */
	public void parse(String file) throws Exception {
		XMIParser parser = new XMIParser();
		Model model = parser.parse(new File(file));
		profiles = model.getProfiles();
		packages = model.getPackages();
		applications = model.getApplications();
	}

	/**
	 * Reset this facade to its original state.
	 */
	public void reset() {
		profiles = null;
	}

	/**
	 * Gets the number of parsed profiles.
	 * 
	 * @return Number of parsed profiles.
	 */
	public int getNumberOfProfiles() {
		return profiles.size();
	}

	/**
	 * Gets a property from a profile.
	 * 
	 * @param profile
	 *            Profile ID.
	 * @param property
	 *            Profile property.
	 * @return The profile property value.
	 * @throws Exception
	 *             If something fails.
	 */
	public String getProfileProperty(String profile, String property)
			throws Exception {
		return BeanUtils.getProperty(profiles.get(profile), property);
	}
	
	/**
	 * Gets the number of stereotypes of a profile.
	 * 
	 * @param profile
	 *            Profile ID.
	 * @return Number of stereotypes of a profile.
	 */
	public int getNumberOfStereotypes(String profile) {
		return profiles.get(profile).getStereotypes().size();
	}

	/**
	 * Gets a stereotype property.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @param property
	 *            Stereotype property.
	 * @return Stereotype property value.
	 * @throws Exception
	 *             If something fails.
	 */
	public String getStereotypeProperty(String profile, String stereotype,
			String property) throws Exception {
		return BeanUtils.getProperty(profiles.get(profile).getStereotypes()
				.get(stereotype), property);
	}

	/**
	 * Verify it a type is associated with a stereotype.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @param type
	 *            UML type name.
	 * @return True if it is associated, false otherwise.
	 */
	public boolean isStereotypeType(String profile, String stereotype,
			String type) {
		return profiles.get(profile).getStereotypes().get(stereotype)
				.getTypes().contains(type);
	}

	/**
	 * Gets the number of associated types that a stereotype contains.
	 * 
	 * @param profile
	 *            Stereotype's profile ID.
	 * @param stereotype
	 *            Stereotype ID.
	 * @return Number of associated types of a stereotype.
	 */
	public int getStereotypeTypeSize(String profile, String stereotype) {
		return profiles.get(profile).getStereotypes().get(stereotype)
				.getTypes().size();
	}

	public int getNumberOfPackages() {
		return packages.size();
	}
	
	public String getPackageProperty(String packageid, String property) throws Exception {
		return BeanUtils.getProperty(packages.get(packageid), property);
	}

	public int getNumberOfMembers(String packageid) {
		return packages.get(packageid).getMembers().keySet().size();
	}
	
	public String getMemberProperty(String packageid, String member,
			String property) throws Exception {
		return BeanUtils.getProperty(packages.get(packageid).getMembers()
				.get(member), property);
	}
	
	public int getNumberOfApplications() {
		return applications.size();
	}
	
	public String getApplicationProperty(int index, String property) throws Exception {
		int i = -1;
		for (StereotypeApplication application : applications) {
			i++;
			if (index == i) {
				return BeanUtils.getProperty(application, property);
			}
		}
		return null;
	}
	
}