package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.abilities.Ability;
import model.abilities.implementation.ComplexAbility;
import model.abilities.implementation.EmptyAbility;
import model.abilities.implementation.SimpleAbility;
import model.cards.Card;
import model.cards.implementation.Creature;
import model.cards.implementation.Item;
import model.cards.implementation.Spell;
import model.entities.Game;
import model.entities.Match;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;
import model.enums.Faction;

public class JsonUtils {

	private static final Map<String, Card> CARD_MAP = readAllCards();

	private JsonUtils() {

	}

	public static void main(String[] args) {
		createGameDeck();
	}

	public static String readCardsJson() {
		try {
			return new String(Files.readAllBytes(Paths.get("src\\main\\resources\\json\\card.json")));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static Map<String, Card> readAllCards() {

		String jsonLine;
		try {
			jsonLine = new String(Files.readAllBytes(Paths.get("src\\main\\resources\\json\\card.json")));
		} catch (IOException e) {
			// TODO decide if this is safe enough
			e.printStackTrace();
			return new HashMap<>();
		}

		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("cards");
		Map<String, Card> cards = new HashMap<>();
		for (JsonElement element : jarray) {
			JsonObject object = element.getAsJsonObject();
			// mandatory
			String name = object.get("name").getAsString();
			Faction faction = Faction.valueOf(object.get("faction").getAsString());
			int cost = Integer.parseInt(object.get("cost").getAsString());
			String code = object.get("code").getAsString();
			String description = object.get("description").getAsString();
			String flavour = object.get("flavour").getAsString();
			String type = object.get("type").getAsString();

			// abilities
			JsonArray jAbilities = object.getAsJsonArray("abilities");
			List<Ability> abilities = parseAbilities(jAbilities);

			// creation
			if (type.equals("SPELL")) {
				cards.put(code, new Spell(abilities, faction, cost, name, code, description));
			} else if (type.equals("ITEM")) {
				cards.put(code, new Item(abilities, faction, cost, name, code, description));
			} else if (type.equals("CREATURE")) {
				int health = Integer.parseInt(object.get("health").getAsString());
				cards.put(code, new Creature(abilities, faction, cost, name, code, description, health));
			}
		}
		return cards;
	}

	private static List<Ability> parseAbilities(JsonArray jAbilities) {
		List<Ability> abilities = new ArrayList<>();
		for (JsonElement element : jAbilities) {
			JsonObject object = element.getAsJsonObject();
			Ability ability = parseAbility(object);
			abilities.add(ability);
		}
		return abilities;
	}

	private static Ability parseAbility(JsonObject object) {
		if (object.has("effect")) {
			return parseComplexAbility(object);
		} else {
			return parseSimpleAbility(object);
		}
	}

	private static Ability parseComplexAbility(JsonObject object) {
		Ability effect = parseAbility(object.getAsJsonObject("effect"));
		Ability precondition = object.has("precondition") ? parseAbility(object.getAsJsonObject("precondition"))
				: EmptyAbility.getInstance();
		Ability postcondition = object.has("postcondition") ? parseAbility(object.getAsJsonObject("postcondition"))
				: EmptyAbility.getInstance();
		AbilityTrigger abilityTrigger = AbilityTrigger.valueOf(object.get("trigger").getAsString());
		return new ComplexAbility(effect, precondition, postcondition, abilityTrigger);
	}

	private static Ability parseSimpleAbility(JsonObject object) {
		AbilityType type = AbilityType.valueOf(object.get("condition").getAsString());
		JsonArray arguments = object.getAsJsonArray("arguments");
		Map<String, Integer> argumentMap = new HashMap<>();
		for (JsonElement argument : arguments) {
			JsonObject argumentObject = argument.getAsJsonObject();
			argumentMap.put(argumentObject.get("key").getAsString(), argumentObject.get("value").getAsInt());
		}
		return new SimpleAbility(type, argumentMap);
	}

	public static List<Card> createGameDeck() {
		return CARD_MAP.values().stream().filter(card -> card.getCost() >= 1 && !card.getCode().equals("FIRGEM"))
				.collect(Collectors.toList());
	}

	public static Card getCardByCode(String code) {
		return CARD_MAP.get(code).copy();
	}

	/**
	 * Creates board state JSON for FE and BE communication.
	 * 
	 * @return board state as JSON
	 */
	public static JsonObject createBoardStateJson(Match match, Game game, String userName) {

		JsonObject boardState = new JsonObject();

		JsonObject matchInfo = new JsonObject();
		matchInfo.addProperty("uuid", match.getUUID());

		// Opponent object
		JsonObject opponent = new JsonObject();
		Player opponentPlayer = game.getPlayers().values().stream().filter(p -> !p.getName().equals(userName))
				.findFirst().get();
		opponent.addProperty("supply", opponentPlayer.get(Player.CURRENT_SUPPLY));
		opponent.addProperty("health", opponentPlayer.get(Player.CURRENT_HEALTH));
		opponent.addProperty("deck", opponentPlayer.getDeck().cardsRemaining());

		JsonArray opponentPermanentArray = new JsonArray();
		for (Creature creature : opponentPlayer.getBoard()) {
			JsonObject permanent = new JsonObject();
			permanent.addProperty("id", creature.getId());
			permanent.addProperty("code", creature.getCode());
			permanent.addProperty("health", creature.get(Creature.CURRENT_HEALTH));
			opponentPermanentArray.add(permanent);
		}
		opponent.add("permanent", opponentPermanentArray);

		opponent.addProperty("handSize", opponentPlayer.getHand().size());
		JsonArray opponentDiscardPileArray = new JsonArray();
		for (Card card : opponentPlayer.getGraveyard()) {
			JsonObject discardCard = new JsonObject();
			discardCard.addProperty("id", card.getId());
			discardCard.addProperty("code", card.getCode());
			opponentDiscardPileArray.add(discardCard);
		}
		opponent.add("discardPile", opponentDiscardPileArray);

		opponent.addProperty("marketSize", opponentPlayer.getMarket().size());

		// Player object
		JsonObject player = new JsonObject();
		Player mainPlayer = game.getPlayers().get(userName);
		player.addProperty("supply", mainPlayer.get(Player.CURRENT_SUPPLY));
		player.addProperty("health", mainPlayer.get(Creature.CURRENT_HEALTH));
		player.addProperty("deck", mainPlayer.getDeck().cardsRemaining());

		JsonArray playerPermanentArray = new JsonArray();
		for (Creature creature : mainPlayer.getBoard()) {
			JsonObject permanent = new JsonObject();
			permanent.addProperty("id", creature.getId());
			permanent.addProperty("code", creature.getCode());
			permanent.addProperty("health", creature.get(Creature.CURRENT_HEALTH));
			playerPermanentArray.add(permanent);
		}
		player.add("permanent", playerPermanentArray);

		// Market object
		JsonArray market = new JsonArray();
		for (Card marketCard : mainPlayer.getMarket()) {
			JsonObject marketObj = new JsonObject();
			marketObj.addProperty("id", marketCard.getId());
			marketObj.addProperty("code", marketCard.getCode());
			market.add(marketObj);
		}
		player.add("market", market);

		JsonArray playerHandArray = new JsonArray();
		for (Card card : mainPlayer.getHand()) {
			JsonObject handCard = new JsonObject();
			handCard.addProperty("id", card.getId());
			handCard.addProperty("code", card.getCode());
			playerHandArray.add(handCard);
		}
		player.add("hand", playerHandArray);

		JsonArray playerDiscardPileArray = new JsonArray();
		for (Card card : mainPlayer.getGraveyard()) {
			JsonObject graveyardCard = new JsonObject();
			graveyardCard.addProperty("id", card.getId());
			graveyardCard.addProperty("code", card.getCode());
			playerDiscardPileArray.add(graveyardCard);
		}
		player.add("discardPile", playerDiscardPileArray);

		// Current player
		JsonObject currentPlayer = new JsonObject();
		currentPlayer.addProperty("username", game.getCurrentPlayer().getName());

		boardState.add("opponent", opponent);
		boardState.add("player", player);
		boardState.add("currentPlayer", currentPlayer);
		boardState.add("match", matchInfo);

		return boardState;
	}

	public static JsonObject createErrorJson(String message) {
		JsonObject errorMessage = new JsonObject();
		errorMessage.addProperty("error", message);
		return errorMessage;
	}
}
