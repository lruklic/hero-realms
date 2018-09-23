package model.entities;

import java.util.Map;

/**
 * 
 * Interface which represents a game
 * 
 * @author Ivan
 *
 */
public interface Game {

	public Player getCurrentPlayer();

	public Map<String, Player> getPlayers();

	public void performAction(String userName, String action, Map<String, Integer> arguments);

	public void start();
}
