package model.entities;

/**
 * 
 * Match is a singleton class which holds the entire game in synchronization. This is the entry point
 * for communication with the front end
 * 
 * @author Ivan
 *
 */
public class Match {

	private static Match instance;
	
	private Game game;
	
	/**
	 * Prevent standard object creation.
	 */
	protected Match() {
	}
	
	public static Match getInstance() {
		if(instance == null) {
			instance = new Match();
		}
		return instance;
	}
	
	public Game getGame() {
		return game;
	}
}
