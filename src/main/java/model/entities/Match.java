package model.entities;

import com.google.gson.JsonObject;

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
			sendBoardState(player.getName());
		}
	}

	public void handleAction(JsonObject message) {
		String userName = message.get("user").getAsString();
		String action = message.get("message").getAsJsonObject().get("action").getAsString();
		// TODO this is a bad way of checking
		int cardId = -1;
		if (message.get("message").getAsJsonObject().get("cardId") != null) {
			cardId = message.get("message").getAsJsonObject().get("cardId").getAsInt();
		}
		game.performAction(userName, action, cardId);
		for (Player player : game.getPlayers().values()) {
			sendBoardState(player.getName());
		}
	}

	public void sendBoardState(String username) {
		MatchWebSocketBridge.sendMessage(username,
				JsonUtils.createBoardStateJson(this, this.game, username).toString());
	}

}
