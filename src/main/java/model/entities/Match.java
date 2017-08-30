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

	private String uuid;

	private Game game;

	public Match(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public Game getGame() {
		return game;
	}

	public void createGame(String firstUsername, String secondUsername) {
		game = new NormalGame(firstUsername, secondUsername);
		for (Player player : game.getPlayers().values()) {
			MatchWebSocketBridge.sendMessage(player.getName(),
					JsonUtils.createBoardStateJson(this, game, player.getName()).toString());
		}
	}

	public void handleAction(String message) {
		String[] information = message.split(" ");
		String userName = information[1];
		String action = information[2];
		int cardId = Integer.parseInt(information[3]);
		game.performAction(userName, action, cardId);
		for (Player player : game.getPlayers().values()) {
			MatchWebSocketBridge.sendMessage(player.getName(),
					JsonUtils.createBoardStateJson(this, game, player.getName()).toString());
		}
	}


}
