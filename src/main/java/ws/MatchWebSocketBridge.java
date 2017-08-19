package ws;

import model.entities.Match;
import utils.Util;

/**
 * Class that connects WebSocket communication with the game itself.
 * 
 * @author LukaRuklic
 *
 */

public class MatchWebSocketBridge {

	public static String createMatch(String player1, String player2) {
		System.out.println("Match started between " + player1 + " and " + player2);

		Match.getInstance().createGame(player1, player2);

		return Util.generateUUID();
	}

	public static void sendMessage(String player, String message) {
		GameWebSocketHandler.sendMessage(player, message);
	}

	public static void receiveMessage(String message) {
		Match.getInstance().handleAction(message);
	}
}
