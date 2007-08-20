package profilechecker.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Represents a model parsed from a XMI file. The model has all the packages, profiles
 * and applications of the XMI file.
 * @author moises
 *
 */
public class Model implements Serializable {
	
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** Map with all the profiles of this model. The key is the profile name */
	private Map<String, Profile> profiles;
	
	/** Map with all the profiles of this model. The key is the package name */
	private Map<String, Package> packages;
	
	/** Set with all the stereotype applications of this model. */
	private Set<StereotypeApplication> applications;
	
	/**
	 * Empty constructor for this JavaBean.
	 */
	public Model() {
		// Empty constructor for this JavaBean
	}
	
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

	/**
	 * Get profiles of this model.
	 * @return Profiles of this model.
	 */
	public Map<String, Profile> getProfiles() {
		return profiles;
	}

	/**
	 * Set profiles of this model.
	 * @param profiles Profiles of this model.
	 */
	public void setProfiles(Map<String, Profile> profiles) {
		this.profiles = profiles;
	}

	/**
	 * Get packages of this model
	 * @return Packages of this model.
	 */
	public Map<String, Package> getPackages() {
		return packages;
	}

	/**
	 * Set packages of this model.
	 * @param packages Packages of this model.
	 */
	public void setPackages(Map<String, Package> packages) {
		this.packages = packages;
	}

	/**
	 * Get stereotype applications of this model.
	 * @return Stereotype applications of this model.
	 */
	public Set<StereotypeApplication> getApplications() {
		return applications;
	}

	/**
	 * Set stereotype applications of this model.
	 * @param applications Stereotype applications of this model.
	 */
	public void setApplications(Set<StereotypeApplication> applications) {
		this.applications = applications;
	}
	
	
	
}