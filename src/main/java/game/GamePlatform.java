package game;

import java.util.ArrayList;
import java.util.List;
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
	
	private GamePlatform() {};
	
	private static List<Match> activeMatches = new ArrayList<>();
	
	public static void addMatch(Match match) {
		activeMatches.add(match);
	}
	
	public static Match getMatchById(String uuid) {
		for (Match match : activeMatches) {
			if (match.getUUID().equals(uuid)) {
				return match;
			}
		}
		
		return null;
	}
	
	public static Match findMatchWithPlayer(String username) {
		for (Match match : activeMatches) {
			for (Map.Entry<String, Player> entry : match.getGame().getPlayers().entrySet()) {
				if (username.equals(entry.getValue().getName())) {
					return match;
				}
			}
		}
		
		return null;
	}
	
}
