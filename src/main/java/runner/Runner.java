package runner;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;
import static spark.Spark.redirect;
import static spark.Spark.webSocket;
import static spark.Spark.webSocketIdleTimeoutMillis;

import utils.JsonUtils;
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

		// System.out.println(createDummyJson().toString());

		get("/cardsJson", (req, res) -> JsonUtils.readCardsJson());

		redirect.get("/", "/index.html");

	}

}
