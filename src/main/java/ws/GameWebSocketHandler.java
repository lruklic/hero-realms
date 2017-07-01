package ws;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import utils.Util;


/**
 * Handler for WebSocket that is used for communication between server (game instance) and browser clients (players).
 * 
 * @author LukaRuklic
 *
 */
@WebSocket
public class GameWebSocketHandler {

	static Map<Session, String> userUsernameMap = new HashMap<>();
	
	@OnWebSocketConnect
	public void onConnect(Session user) throws Exception {

		String[] urlParts = user.getUpgradeRequest().getRequestURI().getPath().split("/");
		String username = urlParts[urlParts.length - 1];
		
		System.out.println("User " + username + " connected.");
       
		//user.getRemote().sendStringByFuture(uuid);
       
    }
	
	@OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        System.out.println("User " + user.toString() + " disconnected because of " + reason);
	}
	
	@OnWebSocketMessage
    public void onMessage(Session user, String message) {
		System.out.println("Message: " + message);
        //Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = message);
    }
	
}
