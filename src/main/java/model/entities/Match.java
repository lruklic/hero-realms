package model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import exceptions.InvalidUserActionException;
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
		List<String> arguments = new ArrayList<>();
		message.getAsJsonArray("arguments").forEach(element -> arguments.add(element.getAsString()));
		Map<String, Integer> argumentMap = new HashMap<>();
		arguments.forEach(element -> argumentMap.put(element, message.get("element").getAsInt()));
		try {
			game.performAction(userName, action, argumentMap);
			for (Player player : game.getPlayers().values()) {
				sendBoardState(player.getName());
			}
		} catch (InvalidUserActionException e) {
			sendErrorMessage(userName, e.getMessage());
		}
	}

	private void sendErrorMessage(String username, String message) {
		MatchWebSocketBridge.sendMessage(username, JsonUtils.createErrorJson(message).toString());
	}

	public void sendBoardState(String username) {
		MatchWebSocketBridge.sendMessage(username,
				JsonUtils.createBoardStateJson(this, this.game, username).toString());
	}

}
