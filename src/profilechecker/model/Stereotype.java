package profilechecker.model;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Stereotype represents a parsed stereotype from a XMI file. It can be
 * associated with 0 or more types.
 * 
 * @author Matheus
 */
public class Stereotype extends OwnedMember {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;

	/** Types associated with this stereotype. */
	private Set<String> types;

	/**
	 * Empty constructor of this JavaBean.
	 */
	public Stereotype() {
		// Empty constructor (JavaBean)
	}

	/**
	 * Creates a basic stereotype. Also initializes the types set as a
	 * LinkedHashSet (using this Collection to control the order which types are
	 * added to this stereotype).
	 * 
	 * @param name
	 *            Name of the stereotype.
	 * @param id
	 *            ID of the stereotype.
	 * @param visibility
	 *            Visibility of the stereotype.
	 * @param line
	 *            Line of this stereotype.
	 */
	public Stereotype(String name, String id, VisibilityType visibility, int line) {
		super(name, id, visibility, line);
		setTypes(new LinkedHashSet<String>());
	}

	/**
	 * Sets the set of UML types which this stereotype can be associated.
	 * 
	 * @param types
	 *            Set of UML types which this stereotype can be associated.
	 */
	public void setTypes(Set<String> types) {
		this.types = types;
	}

	/**
	 * Gets the set of UML types which this stereotype can be associated.
	 * 
	 * @return Set of UML types which this stereotype can be associated.
	 */
	public Set<String> getTypes() {
		return this.types;
	}

	/**
	 * Add an UML type that can be associated with this stereotype.
	 * 
	 * @param type
	 *            UML type that can be associated with this stereotype.
	 */
	public void addType(String type) {
		this.types.add(type);
	}

}