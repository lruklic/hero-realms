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
import model.abilities.implementation.AbilityFactory;
import model.cards.Card;
import model.cards.implementation.Action;
import model.cards.implementation.Champion;
import model.cards.implementation.Item;
import model.entities.Game;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;
import model.enums.Faction;
import model.enums.HeroClass;

public class JsonUtils {

	private static final Map<String, Card> CARD_MAP = readAllCards();

	private JsonUtils() {

	}

	public static void main(String[] args) {
		createGameDeck();
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
			String name = object.get("name").getAsString();
			Faction faction = Faction.valueOf(object.get("faction").getAsString());
			int cost = Integer.parseInt(object.get("cost").getAsString());
			JsonArray jAbilities = object.getAsJsonArray("abilities");
			List<Ability> abilities = parseAbilities(jAbilities);
			// TODO change code parsing when it's available for all cards
			String code = object.get("code") == null ? null : object.get("code").getAsString();
			String description = object.get("description").getAsString();
			HeroClass heroClass = HeroClass.valueOf(object.get("class").getAsString());
			String type = object.get("type").getAsString();
			if (type.equals("ACTION")) {
				cards.put(name, new Action(abilities, faction, cost, name, code, description, heroClass));
			} else if (type.equals("ITEM")) {
				cards.put(name, new Item(abilities, faction, cost, name, code, description, heroClass));
			} else if (type.equals("CHAMPION")) {
				int health = Integer.parseInt(object.get("health").getAsString());
				boolean isGuard = object.get("guard").getAsString().equals("YES");
				cards.put(name,
						new Champion(abilities, faction, cost, name, code, description, heroClass, isGuard, health));
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

	private static boolean isSimpleAbilityType(String abilityType) {
		try {
			AbilityType.valueOf(abilityType);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private static Ability parseAbility(JsonObject object) {
		String abilityType = object.get("type").getAsString();
		if (isSimpleAbilityType(abilityType)) {
			return parseSimpleAbility(object);
		} else if (abilityType.startsWith("REPEATING")) {
			return parseRepeatingAbility(object);
		} else if (abilityType.contains("CHOICE")) {
			return parseChoiceAbility(object);
		} else if (abilityType.contains("COMPOSITE")) {
			return parseCompositeAbility(object);
		} else if (abilityType.contains("CONDITIONAL")) {
			return parseConditionalAbility(object);
		} else if (abilityType.contains("EMPTY")) {
			return AbilityFactory.getEmptyAbility();
		} else {
			// throw new IllegalArgumentException("Unknown ability type
			// provided: " + abilityType);
			// TODO change this when all abilities are covered
			return AbilityFactory.getEmptyAbility();
		}
	}

	private static Ability parseConditionalAbility(JsonObject object) {
		String condition = object.get("condition").getAsString();
		Ability fulfilledAbility = parseAbility(object.getAsJsonObject("fulfilled"));
		Ability unfulfilledAbility = parseAbility(object.getAsJsonObject("unfulfilled"));
		return AbilityFactory.getConditionalAbility(fulfilledAbility, unfulfilledAbility, condition);
	}

	private static Ability parseChoiceAbility(JsonObject object) {
		JsonArray jAbilities = object.getAsJsonArray("abilities");
		List<Ability> abilities = parseAbilities(jAbilities);
		return AbilityFactory.getChoiceAbility(abilities);
	}

	private static Ability parseCompositeAbility(JsonObject object) {
		JsonArray jAbilities = object.getAsJsonArray("abilities");
		List<Ability> abilities = parseAbilities(jAbilities);
		return AbilityFactory.getCompositeAbility(abilities);
	}

	private static Ability parseRepeatingAbility(JsonObject object) {
		String abilityType = object.get("type").getAsString();
		String counter = abilityType.split("REPEATING")[1];
		Ability ability = parseAbility(object.get("ability").getAsJsonObject());
		return AbilityFactory.getRepeatingAbility(ability, counter);
	}

	private static Ability parseSimpleAbility(JsonObject object) {
		return AbilityFactory.getSimpleAbility(AbilityType.valueOf(object.get("type").getAsString()),
				AbilityTrigger.valueOf(object.get("trigger").getAsString()));
	}

	public static List<Card> createGameDeck() {
		return CARD_MAP.values().stream().filter(card -> card.getCost() >= 1).collect(Collectors.toList());
	}

	public static Card getCardByName(String name) {
		return CARD_MAP.get(name);
	}

	/**
	 * Creates board state JSON for FE and BE communication.
	 * 
	 * @return board state as JSON
	 */
	public static JsonObject createBoardStateJson(Game game, String userName) {

		JsonObject boardState = new JsonObject();

		// Opponent object
		JsonObject opponent = new JsonObject();
		Player opponentPlayer = game.getPlayers().values().stream().filter(p -> !p.getName().equals(userName))
				.findFirst().get();
		opponent.addProperty("gold", opponentPlayer.getGold());
		opponent.addProperty("health", opponentPlayer.getHealth());
		opponent.addProperty("combat", opponentPlayer.getDamage());

		JsonArray opponentPermanentArray = new JsonArray();

		for (Champion champion : opponentPlayer.getBoard()) {
			JsonObject permanent = new JsonObject();
			permanent.addProperty("id", champion.getId());
			permanent.addProperty("code", champion.getCode());
			permanent.addProperty("health", champion.getHealth());
			opponentPermanentArray.add(permanent);
		}
		opponent.add("permanent", opponentPermanentArray);

		JsonArray opponentNonpermanentArray = new JsonArray();

		for (Card card : opponentPlayer.getActions()) {
			JsonObject nonpermanent = new JsonObject();
			nonpermanent.addProperty("id", card.getId());
			nonpermanent.addProperty("code", card.getCode());
			opponentNonpermanentArray.add(nonpermanent);
		}
		opponent.add("nonpermanent", opponentNonpermanentArray);

		// Market object
		JsonArray market = new JsonArray();

		for (Card marketCard : game.getMarket()) {
			JsonObject marketObj = new JsonObject();
			marketObj.addProperty("id", marketCard.getId());
			marketObj.addProperty("code", marketCard.getCode());
			market.add(marketObj);
		}

		// Player object
		JsonObject player = new JsonObject();
		Player mainPlayer = game.getPlayers().get(userName);
		player.addProperty("gold", mainPlayer.getGold());
		player.addProperty("health", mainPlayer.getHealth());
		player.addProperty("combat", mainPlayer.getDamage());

		JsonArray playerPermanentArray = new JsonArray();

		for (Champion champion : mainPlayer.getBoard()) {
			JsonObject permanent = new JsonObject();
			permanent.addProperty("id", champion.getId());
			permanent.addProperty("code", champion.getCode());
			permanent.addProperty("health", champion.getHealth());
			playerPermanentArray.add(permanent);
		}
		player.add("permanent", playerPermanentArray);

		JsonArray playerNonpermanentArray = new JsonArray();

		for (Card card : mainPlayer.getActions()) {
			JsonObject nonpermanent = new JsonObject();
			nonpermanent.addProperty("id", card.getId());
			nonpermanent.addProperty("code", card.getCode());
			playerNonpermanentArray.add(nonpermanent);
		}
		player.add("nonpermanent", playerNonpermanentArray);

		boardState.add("opponent", opponent);
		boardState.add("market", market);
		boardState.add("player", player);

		return boardState;
	}
}
