package exceptions;

import model.entities.Player;

public class InvalidUserActionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7106343727304622769L;

	private Player offendingPlayer;

	private InvalidUserActionException() {
		super();
	}

	public InvalidUserActionException(String message, Player player) {
		super(message);
		offendingPlayer = player;
	}

	public Player getOffendingPlayer() {
		return offendingPlayer;
	}
}
