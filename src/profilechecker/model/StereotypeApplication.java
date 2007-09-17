package profilechecker.model;


/**
 * Represents stereotype application to a member.
 * @author moises
 *
 */
public class StereotypeApplication extends LineMember {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The id of the application */
	private String id;
	
	/** The base type of the application */
	private String base;
	
	/** The id of the base type of the application */
	private String baseId;
	
	/** The name of the stereotype of this application */
	private String stereotype;
	
	/** The name of the profile of this application */
	private String profile;
	
	/**
	 * Empty constructor of this JavaBean.
	 */
	public StereotypeApplication(){
		// Empty constructor for this JavaBean
	}
	
	/**
	 * Basic constructor that recieves the id, base, baseId, stereotype name and
	 * profile name.
	 * @param id id of the application
	 * @param base base type of the application
	 * @param baseId id of the base type of the application
	 * @param stereotype name of the stereotype of this application
	 * @param profile name of the profile of this application
	 */
	public StereotypeApplication(String id, String base, 
			String baseId, String stereotype, String profile){
		this.id = id;
		this.base = base;
		this.baseId = baseId;
		this.stereotype = stereotype;
		this.profile = profile;
	}

	/**
	 * Returns the id of the application.
	 * @return the id of the application
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the base type of the application.
	 * @return the base type of the application
	 */
	public String getBase() {
		return base;
	}

	/**
	 * Returns the id of the base type of the application.
	 * @return the id of the base type of the application
	 */
	public String getBaseId() {
		return baseId;
	}

	/**
	 * Returns the name of the stereotype of this application.
	 * @return the name of the stereotype of this application
	 */
	public String getStereotype() {
		return stereotype;
	}
	
	/**
	 * Returns the name of the profile of this application.
	 * @return the name of the profile of this application
	 */
	public String getProfile() {
		return profile;
	}	
}