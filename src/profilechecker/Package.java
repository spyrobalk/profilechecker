package profilechecker;

import java.util.HashMap;
import java.util.Map;

/**
 * Package represents a parsed package from a XMI file. A package can have 
 * 0 or more profiles applied to it. 
 * 
 * @author moises
 *
 */
public class Package extends OwnedMember{

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map of profiles applied to this Package. 
	 */
	private Map<String,Profile> profiles;
	
	/**
	 * Map of classes that are in this Package.
	 */
	private Map<String,Member> members;
	
	/**
	 * Empty constructor of this JavaBean.
	 */
	public Package(){
		
	}
	
	/**
	 * Basic constructor that will create a Package using the name, id and visibility
	 * associated with the package and initializes two maps. One for the profiles and
	 * the other for the members.
	 * @param name the name of the package
	 * @param id the id of the package
	 * @param visibility the visibility of the package
	 */
	public Package (String name, String id, VisibilityType visibility) {
		super(name, id, visibility);
		setProfiles(new HashMap<String, Profile>());
		setMembers(new HashMap<String, Member>());
	}

	/**
	 * Sets a map of strings and profiles as this package profiles map.
	 * @param name map with a string as key and a profile as a value
	 */
	public void setProfiles(HashMap<String, Profile> name) {
		profiles = name;
	}
	
	/**
	 * Sets a map of strings and members as this package profiles map.
	 * @param name map with a string as key and a member as a value
	 */
	public void setMembers(HashMap<String, Member> name) {
		members = name;
	}
	
	/**
	 * Adds a profile to this package.
	 * @param id added profile's id
	 * @param p the profile to be added
	 */
	public void addProfile(String id, Profile p){ 
		profiles.put(id,p);
	}
	
	/**
	 * Returns the map with a string as key and profiles as values that have the
	 * profiles of this package. 
	 * @return the map with a string as key and profiles as values that have the
	 * profiles of this package
	 */
	public Map<String,Profile> getProfiles(){
		return this.profiles;
	}
	
	/**
	 * Returns the map with a string as key and members as values that have the
	 * members of this package. 
	 * @return the map with a string as key and members as values that have the
	 * members of this package
	 */
	public Map<String,Member> getMembers(){
		return this.members;
	}
}