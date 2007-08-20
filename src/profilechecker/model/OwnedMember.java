package profilechecker.model;

import java.io.Serializable;

/**
 * OwnedMember is a XMI member that contains a name, id and visibility.
 * 
 * @author Matheus
 */
public abstract class OwnedMember implements Serializable {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;

	/** Name of this member, can be null. */
	private String name;

	/** ID of this member. Cannot be null, and should be unique among the XMI. */
	private String id;

	/** Visibility of this member. */
	private VisibilityType visibility;

	/**
	 * Empty constructor of this JavaBean.
	 */
	public OwnedMember() {
		// Empty constructor (JavaBean)
	}

	/**
	 * Created a basic OwnedMember.
	 * 
	 * @param name
	 *            Name of this OwnedMember.
	 * @param id
	 *            ID of this OwnedMember.
	 * @param visibility
	 *            Visibility of this OwnedMember.
	 */
	public OwnedMember(String name, String id, VisibilityType visibility) {
		setName(name);
		setId(id);
		setVisibility(visibility);
	}

	/**
	 * Gets the OwnedMember name.
	 * 
	 * @return The OwnedMember name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the OwnedMember name.
	 * 
	 * @param name
	 *            The OwnedMember name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the OwnedMember ID.
	 * 
	 * @return The OwnedMember ID.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the OwnedMember ID. It cannot be null.
	 * 
	 * @param id
	 *            The OwnedMember ID.
	 */
	public void setId(String id) {
		if (id == null) {
			throw new NullPointerException("ID property must not be null");
		}
		this.id = id;
	}

	/**
	 * Gets the OwnedMember visibility.
	 * 
	 * @return The OwnedMember visibility.
	 */
	public VisibilityType getVisibility() {
		return visibility;
	}

	/**
	 * Sets the OwnedMember visibility.
	 * 
	 * @param visibility
	 *            The OwnedMember visibility.
	 */
	public void setVisibility(VisibilityType visibility) {
		if (visibility == null) {
			throw new NullPointerException("Visibility property cannot be null");
		}
		this.visibility = visibility;
	}

}