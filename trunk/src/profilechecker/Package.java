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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map of profiles applied to this Package. 
	 */
	private Map<String,Profile> profiles;
	
	/**
	 * Map of classes that are in this Package.
	 */
	private Map<String,Member> members;
	
	public Package(){
		
	}
	
	public Package (String name, String id, VisibilityType visibility) {
		super(name, id, visibility);
		setProfiles(new HashMap<String, Profile>());
		setMembers(new HashMap<String, Member>());
	}

	public void setProfiles(HashMap<String, Profile> name) {
		profiles = name;
	}
	
	public void setMembers(HashMap<String, Member> name) {
		members = name;
	}
	
	public void addProfile(String id, Profile p){ 
		profiles.put(id,p);
	}
	
	public Map<String,Profile> getProfiles(){
		return this.profiles;
	}
	
	public Map<String,Member> getMembers(){
		return this.members;
	}
	
	public void addClass(String id, Member member){
		members.put(id, member);
	}

}