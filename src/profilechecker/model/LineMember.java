package profilechecker.model;

import java.io.Serializable;

/**
 * A line member represents a member that has a line associated
 * at the respective XMI parsed file.
 *
 * @author Matheus
 */
public abstract class LineMember implements Serializable {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** Line in the source code which this OwnedMember appears. */
	private int line;

	/**
	 * Default constructor.
	 */
	public LineMember() {
		// Empty constructor of a java Bean.
	}

	/**
	 * Get the current line where this OwnedMember appears.
	 * 
	 * @return Current line where this OwnedMember appears.
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * Set the current line of this OwnedMember.
	 * 
	 * @param line
	 *            Current line of this OwnedMember.
	 */
	protected void setLine( int line ) {
		this.line = line;
	}

}
