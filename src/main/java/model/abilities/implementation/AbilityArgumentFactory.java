package model.abilities.implementation;

import java.util.HashMap;
import java.util.Map;

import model.abilities.Ability;
import model.abilities.AbilityArgument;
import model.enums.AbilityType;

public class AbilityArgumentFactory {

	public static AbilityArgument getArgument(Ability ability, AbilityType type) {
		return new AbilityArgumentConcrete(ability, type);
	}

	private static class AbilityArgumentConcrete implements AbilityArgument {

		private static Map<String, String> argumentMap;

		private AbilityArgumentConcrete(Ability ability, AbilityType type) {
			argumentMap = new HashMap<>();
			type.getArguments().forEach(argument -> argumentMap.put(argument, ability.get(argument).toString()));
		}

		@Override
		public String getArgument(String key) {
			return argumentMap.get(key);
		}
	}
}
