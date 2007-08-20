package profilechecker.model;

import java.io.Serializable;

/**
 * This enumeration contains the four possibilities of a visibility attribute of
 * an OwnedMember. They are: public, private, protected and package.
 * 
 * @author Matheus
 */
public enum VisibilityType implements Serializable {

	/** Public visibility. */
	publicV,

	/** Private visibility. */
	privateV,

	/** Protected visibility. */
	protectedV,

	/** Package visibility. */
	packageV;

	/**
	 * Converts a value to this enumeration. This is done since the current
	 * valueOf of this enumeration would fail since all the values are reserved
	 * words in java programming language.
	 * 
	 * @param value
	 *            Value to be parsed.
	 * @return The enumeration associated with this value.
	 * @throws IllegalArgumentException
	 *             If this value cannot be parsed.
	 */
	public static VisibilityType toValue(String value) {
		if ("public".equalsIgnoreCase(value)) {
			return publicV;
		}
		if ("private".equalsIgnoreCase(value)) {
			return privateV;
		}
		if ("protected".equalsIgnoreCase(value)) {
			return protectedV;
		}
		if ("package".equalsIgnoreCase(value)) {
			return packageV;
		}
		throw new IllegalArgumentException("Cannot parse value: " + value);
	}

}