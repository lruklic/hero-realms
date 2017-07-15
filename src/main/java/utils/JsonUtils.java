package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.abilities.Ability;
import model.abilities.implementation.AbilityFactory;
import model.cards.Card;
import model.cards.implementation.CardImplementation;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;
import model.enums.Faction;

public class JsonUtils {

	public static void main(String[] args) {
		try {
			parse(new String(
					Files.readAllBytes(Paths.get("C:\\HR\\hero-realms\\src\\main\\resources\\json\\card.json"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void parse(String jsonLine) {
		JsonElement jelement = new JsonParser().parse(jsonLine);
		JsonObject jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("cards");
		List<Card> cards = new ArrayList<>();
		for (JsonElement element : jarray) {
			JsonObject object = element.getAsJsonObject();
			String name = object.get("name").getAsString();
			Faction faction = Faction.valueOf(object.get("faction").getAsString());
			int cost = Integer.parseInt(object.get("cost").getAsString());
			JsonArray jAbilities = object.getAsJsonArray("abilities");
			List<Ability> abilities = parseAbilities(jAbilities);
			cards.add(new CardImplementation(abilities, faction, cost, name) {

				@Override
				public void goIntoPlay(Player player) {
					// TODO Auto-generated method stub

				}
			});
		}
		System.out.println();
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
			throw new IllegalArgumentException("Unknown ability type provided: " + abilityType);
		}
	}

	private static Ability parseConditionalAbility(JsonObject object) {
		JsonArray jAbilities = object.getAsJsonArray("abilities");
		String condition = object.get("condition").getAsString();
		List<Ability> abilities = parseAbilities(jAbilities);
		return AbilityFactory.getConditionalAbility(abilities.get(0), abilities.get(1), condition);
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
}
