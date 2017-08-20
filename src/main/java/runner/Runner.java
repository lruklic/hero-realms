package runner;

import static spark.Spark.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import utils.JsonUtils;
import utils.Util;
import ws.GameWebSocketHandler;

/**
 * Starting point of web application.   
 * 
 * @author lruklic
 *
 */
  
public class Runner {

	public static void main(String[] args) {
		
		System.out.println("Server started on port 4567 ...");
		
		externalStaticFileLocation("src/main/resources/public");
		
		webSocket("/gamesocket/*", GameWebSocketHandler.class);
		webSocketIdleTimeoutMillis(5 * 60000);

		//System.out.println(createDummyJson().toString());
		
		get("/cardsJson", (req, res) -> JsonUtils.readCardsJson());
		
		redirect.get("/", "/index.html");
		
	}
	
}
