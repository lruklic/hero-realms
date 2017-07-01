package runner;

import static spark.Spark.*;

import ws.GameWebSocketHandler;

/**
 * Starting point of web application.   
 * 
 * @author lruklic
 *
 */
  
public class Runner {

	public static void main(String[] args) {
		
		System.out.println("Server started ...");
		
		externalStaticFileLocation("src/main/resources/public");
		
		webSocket("/gamesocket/*", GameWebSocketHandler.class);
		webSocketIdleTimeoutMillis(5 * 60000);

		redirect.get("/", "/index.html");
		
	}
	
}
