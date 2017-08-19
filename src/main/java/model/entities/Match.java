package model.entities;

import model.entities.implementation.NormalGame;
import utils.JsonUtils;
import ws.MatchWebSocketBridge;

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
		for (Player player : game.getPlayers().values()) {
			MatchWebSocketBridge.sendMessage(player.getName(),
					JsonUtils.createBoardStateJson(game, player.getName()).toString());
		}
	}

	public void handleAction(String message) {
		String[] information = message.split(" ");
		String userName = information[0];
		String action = information[1];
		int cardId = Integer.parseInt(information[2]);
		game.performAction(userName, action, cardId);
		for (Player player : game.getPlayers().values()) {
			MatchWebSocketBridge.sendMessage(player.getName(),
					JsonUtils.createBoardStateJson(game, player.getName()).toString());
		}
	}
}
