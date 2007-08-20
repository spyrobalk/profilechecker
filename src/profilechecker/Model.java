package profilechecker;

import java.util.Map;
import java.util.Set;

public class Model {
	
	private Map<String, Profile> profiles;
	private Map<String, Package> packages;
	private Set<StereotypeApplication> applications;
	
	public Model (Map<String, Profile> profiles,Map<String, Package> packages,
					Set<StereotypeApplication> applications){
		this.profiles = profiles;
		this.packages = packages;
		this.applications = applications;
	}
}
