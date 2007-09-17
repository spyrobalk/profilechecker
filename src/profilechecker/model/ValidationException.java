package profilechecker.model;

/**
 * <code>ValidationException</code> is used to identify a stereotype
 * application exception.
 * 
 * @author Matheus
 */
public class ValidationException extends Exception {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/** Level of the exception. */
	private Level level;

	/**
	 * Default constructor.
	 * 
	 * @param message
	 *            Reason which the validation has failed.
	 * @param level
	 *            Level of the exception.
	 */
	public ValidationException(String message, Level level) {
		super(message);
		this.level = level;
	}
	
	/**
	 * Returns the Level of this exception.
	 * @return The level of this exception.
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Describes the severity level of an exception.
	 */
	public enum Level {
		/** Warnings are bad practices adopted at a XMI model. */
		warning,
		
		/** An error describes a bad stereotype application. */
		error,
		
		/** Fatal describes an unrecoverable error. */
		fatal;
	}
	
}