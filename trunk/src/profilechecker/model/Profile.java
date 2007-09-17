package profilechecker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Profile represents a parsed profile from a XMI file. It can have 0 or more
 * stereotypes and also is used to define the namespace of the applied
 * stereotype.
 * 
 * @author Matheus
 */
public class Profile extends OwnedMember {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;

	/** Map of stereotypes of this profile. */
	private Map<String, Stereotype> stereotypes;

	/**
	 * Empty constructor of this JavaBean.
	 */
	public Profile() {
		// Empty constructor (JavaBean)
	}

	/**
	 * Basic constructor of this profile, it will initialize an empty map of
	 * stereotypes.
	 * 
	 * @param name
	 *            This profile name.
	 * @param id
	 *            This profile ID.
	 * @param visibility
	 *            This profile visibility.
	 * @param line
	 *            Current line of this profile.
	 */
	public Profile(String name, String id, VisibilityType visibility, int line) {
		super(name, id, visibility, line);
		setStereotypes(new HashMap<String, Stereotype>());
	}

	/**
	 * Gets the map of stereotypes of this profile. The key is the ID of the
	 * stereotype.
	 * 
	 * @return The map of stereotypes of this profile.
	 */
	public Map<String, Stereotype> getStereotypes() {
		return stereotypes;
	}

	/**
	 * Sets a map of stereotypes of this profile.
	 * 
	 * @param stereotypes
	 *            Map of stereotypes of this profile.
	 */
	public void setStereotypes(Map<String, Stereotype> stereotypes) {
		this.stereotypes = stereotypes;
	}

	/**
	 * Add a stereotype to the map of stereotypes of this profile.
	 * 
	 * @param id
	 *            ID of the stereotype.
	 * @param stereotype
	 *            Stereotype to be added.
	 */
	public void addStereotype(String id, Stereotype stereotype) {
		this.stereotypes.put(id, stereotype);
	}

}