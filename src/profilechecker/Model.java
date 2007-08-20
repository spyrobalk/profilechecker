package profilechecker;

import java.util.Map;
import java.util.Set;

/**
 * Represents a model parsed from a XMI file. The model has all the packages, profiles
 * and applications of the XMI file.
 * @author moises
 *
 */
public class Model {
	
	/** Map with all the profiles of this model. The key is the profile name */
	private Map<String, Profile> profiles;
	
	/** Map with all the profiles of this model. The key is the package name */
	private Map<String, Package> packages;
	
	/** Set with all the stereotype applications of this model. */
	private Set<StereotypeApplication> applications;
	
	/**
	 * Creates a very simples model with the collections received. 
	 * @param profiles map with all the profiles for this model
	 * @param packages map with all the packages for this model
	 * @param applications set with the stereotype applications for this model
	 */
	public Model (Map<String, Profile> profiles,Map<String, Package> packages,
					Set<StereotypeApplication> applications){
		this.profiles = profiles;
		this.packages = packages;
		this.applications = applications;
	}
}
