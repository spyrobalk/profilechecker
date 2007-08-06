package profilechecker.parser;

import java.io.File;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import profilechecker.Profile;

/**
 * EasyAccept facade to use the XMIParser.
 * 
 * @author Matheus
 */
public class XMIParserFacade {

	/** Map of parsed profiles. */
	private Map<String, Profile> profiles;

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
		XMIParser parser = new XMIParser(new File(file));
		profiles = parser.parse();
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

}