package exceptions;

public class InvalidUserActionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7106343727304622769L;

	public InvalidUserActionException(String message) {
		super(message);
	}
}
