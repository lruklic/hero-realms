package model.entities;

import model.entities.implementation.NormalGame;
import ws.GameWebSocketHandler;

/**
 * 
 * Match is a singleton class which holds the entire game in synchronization.
 * This is the entry point for communication with the front end
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
	private Match() {
	}

	public static Match getInstance() {
		if (instance == null) {
			instance = new Match();
		}
		return instance;
	}

	public Game getGame() {
		return game;
	}

	public void createGame(String firstUsername, String secondUsername) {
		game = new NormalGame(firstUsername, secondUsername);
	}

	public void sendMessage(String message, Player player) {
		GameWebSocketHandler.sendMessage(message, player.getName());
	}

	public void getMessage(String message, String player) {

	}
}
