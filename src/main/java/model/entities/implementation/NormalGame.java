package model.entities.implementation;

import java.util.HashMap;
import java.util.Map;

import exceptions.InvalidUserActionException;
import model.entities.Game;
import model.entities.Player;

/**
 * 
 * This class represents a normal, 50 health, one on one game
 * 
 * @author Ivan
 *
 */
public class NormalGame implements Game {

	private Player currentPlayer;

	private Map<String, Player> players;

	public NormalGame(String firstUsername, String secondUsername) {
		if (firstUsername.equals(secondUsername)) {
			throw new IllegalArgumentException("Player names cannot be the same!");
		}
		Player firstPlayer = new PlayerImplementation(firstUsername);
		Player secondPlayer = new PlayerImplementation(secondUsername);
		currentPlayer = firstPlayer;
		players = new HashMap<>();
		players.put(firstUsername, firstPlayer);
		players.put(secondUsername, secondPlayer);
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public Map<String, Player> getPlayers() {
		return players;
	}

	@Override
	public void start() {
		currentPlayer.startTurn();
	}

	@Override
	public void performAction(String userName, String action, Map<String, Integer> arguments) {
		Player player = players.get(userName);
		if (!player.equals(getCurrentPlayer())) {
			throw new InvalidUserActionException("It is not your turn!");
		}
		arguments.forEach((K, V) -> player.put(K, V));
		player.takeMessage(action);
	}
}
