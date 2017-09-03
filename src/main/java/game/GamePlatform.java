package game;

import java.util.HashMap;
import java.util.Map;

import model.entities.Match;
import model.entities.Player;

/**
 * Class that stores all active matches.
 * 
 * @author LukaRuklic
 *
 */

public class GamePlatform {

	private GamePlatform() {
	};

	private static final Map<String, Match> activeMatches = new HashMap<>();

	public static void addMatch(Match match) {
		activeMatches.put(match.getUUID(), match);
	}

	public static Match getMatchById(String uuid) {
		return activeMatches.get(uuid);
	}

	public static Match findMatchWithPlayer(String username) {
		for (Match match : activeMatches.values()) {
			for (Player player : match.getGame().getPlayers().values()) {
				if (player.getName().equals(username)) {
					return match;
				}
			}
		}
		return null;
	}

}
