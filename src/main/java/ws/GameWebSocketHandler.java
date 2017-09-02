package ws;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import game.GamePlatform;
import model.entities.Match;

/**
 * Handler for WebSocket that is used for communication between server (game
 * instance) and browser clients (players).
 * 
 * @author LukaRuklic
 *
 */
@WebSocket
public class GameWebSocketHandler {

	static Map<String, Session> usernameSessionMap = new HashMap<>();

	@OnWebSocketConnect
	public void onConnect(Session session) throws Exception {

		String[] urlParts = session.getUpgradeRequest().getRequestURI().getPath().split("/");
		String username = urlParts[urlParts.length - 1];

		if (!usernameSessionMap.containsKey(session)) {
			usernameSessionMap.put(username, session);
		}
		
		// Player is already in the game
		Match existingMatch = GamePlatform.findMatchWithPlayer(username);
		if (existingMatch != null) {
			System.out.println("Player " + username + " reconnected.");
			existingMatch.sendBoardState(username);
			return;
		}

		System.out.println("User " + username + " connected.");

		if (usernameSessionMap.values().size() == 2) {
			MatchWebSocketBridge.createMatch((String) usernameSessionMap.keySet().toArray()[0],
					(String) usernameSessionMap.keySet().toArray()[1]);
		}

	}

	@OnWebSocketClose
	public void onClose(Session session, int statusCode, String reason) {
		System.out.println("User " + session.toString() + " disconnected because of " + reason);
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) {
		System.out.println("Message: " + message);
		MatchWebSocketBridge.receiveMessage(message);
	}

	public static void broadcastMessage() {

	}

	public static void sendMessage(String username, String message) {
		Session userSession = usernameSessionMap.get(username);
		if (userSession == null) {
			// throw NoSuchUser
			return;
		}
		userSession.getRemote().sendString(message, new WriteCallback() {

			@Override
			public void writeSuccess() {
				// TODO Auto-generated method stub

			}

			@Override
			public void writeFailed(Throwable x) {
				// TODO Auto-generated method stub

			}
		});
	}

}
