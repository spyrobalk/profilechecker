package profilechecker;

import java.util.Map;

public class StereotypeApplication {

	private String id;
	private String base;
	private String baseId;
	private String stereotype;
	private String profile;
	
	public StereotypeApplication(){
		
	}
	
	public StereotypeApplication(String id, String base, 
			String baseId, String stereotype, String profile){
		this.id = id;
		this.base = base;
		this.baseId = baseId;
		this.stereotype = stereotype;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public String getBase() {
		return base;
	}

	public String getBaseId() {
		return baseId;
	}

	public String getStereotype() {
		return stereotype;
	}
	
	public String getProfile() {
		return profile;
	}
	
	
}
