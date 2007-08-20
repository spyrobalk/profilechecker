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

	/**
	 * Default constructor.
	 * 
	 * @param message
	 *            Reason which the validation has failed.
	 */
	public ValidationException(String message) {
		super(message);
	}

}
