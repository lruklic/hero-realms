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
		
		// Match.getInstance().startGame();
		
		GameWebSocketHandler.sendMessage(player1, player1 + "'s turn!");
		GameWebSocketHandler.sendMessage(player2, player1 + "'s turn!");
		
		return Util.generateUUID();
	}
	
}
