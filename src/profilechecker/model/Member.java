package profilechecker.model;

/**
 * Represents a member of a package.
 * @author moises
 *
 */
public class Member extends OwnedMember{
	
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The id of the member. */
	private String id;
	
	/** The type of the member. */
	private String type;
	
	/**
	 * Empty constructor of this JavaBean.
	 */
	public Member(){
		// Empty constructor for the JavaBean.
	}
	
	/**
	 * Basic constructor that uses the name, id, visibility and type to build
	 * a member.
	 * @param name the name of the member
	 * @param id the id of the member
	 * @param visibility the visibility of the member
	 * @param type the type of the member
	 */
	public Member(String name, String id, VisibilityType visibility, String type){
		super(name,id,visibility);
		this.id = id;
		this.type = type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the type of this member.
	 * @return the type of this member
	 */
	public String getType() {
		return type;
	}

	/**
	 * Modifies the type of this member.
	 * @param type this member's new type
	 */
	public void setType(String type) {
		this.type = type;
	}
}