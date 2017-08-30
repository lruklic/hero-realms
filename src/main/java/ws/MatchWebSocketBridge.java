package ws;

import game.GamePlatform;
import model.entities.Match;
import utils.Util;

/**
 * Class that connects WebSocket communication with the game itself.
 * 
 * @author LukaRuklic
 *
 */

public class MatchWebSocketBridge {

	public static Match createMatch(String player1, String player2) {
		System.out.println("Match started between " + player1 + " and " + player2);

		Match match = new Match(Util.generateUUID());
		match.createGame(player1, player2);

		GamePlatform.addMatch(match);
		
		return match;
	}

	public static void sendMessage(String player, String message) {
		GameWebSocketHandler.sendMessage(player, message);
	}

	public static void receiveMessage(String message) {
		Match match = GamePlatform.getMatchById(message.split(" ")[0]);
		match.handleAction(message);
	}
}
