package model.abilities.implementation;

import java.util.List;

import model.abilities.Ability;
import model.abilities.AbilityCondition;
import model.abilities.AbilityCounter;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;

public class AbilityFactory {

	private AbilityFactory() {

	}

	public static Ability getSimpleAbility(AbilityType type, AbilityTrigger trigger) {
		return new SimpleAbility(type, trigger);
	}

	public static Ability getRepeatingAbility(Ability ability, int count) {
		return new RepeatingAbility(ability, new AbilityCounter() {

			@Override
			public int getNumberOfOccurences(Player player) {
				return count;
			}
		});
	}

	public static Ability getRepeatingAbility(Ability ability, AbilityCounter counter) {
		return new RepeatingAbility(ability, counter);
	}

	public static Ability getRepeatingAbility(Ability ability, String counter) {
		return getRepeatingAbility(ability, parseAbilityCounter(counter));
	}

	public static Ability getEmptyAbility() {
		return EmptyAbility.getInstance();
	}

	public static Ability getCompositeAbility(List<Ability> abilities) {
		return new CompositeAbility(abilities);
	}

	public static Ability getChoiceAbility(List<Ability> abilities) {
		return new ChoiceAbility(abilities);
	}

	public static Ability getConditionalAbility(Ability fulfilledAbility, Ability unfulfilledAbility,
			AbilityCondition condition) {
		return new ConditionalAbility(fulfilledAbility, unfulfilledAbility, condition);
	}

	public static Ability getConditionalAbility(Ability fulfilledAbility, Ability unfulfilledAbility,
			String condition) {
		return new ConditionalAbility(fulfilledAbility, unfulfilledAbility, parseAbilityCondition(condition));
	}

	private static AbilityCondition parseAbilityCondition(String condition) {
		// TODO
		return null;
	}

	private static AbilityCounter parseAbilityCounter(String counter) {
		try {
			return new AbilityCounter() {

				@Override
				public int getNumberOfOccurences(Player player) {
					return Integer.parseInt(counter);
				}
			};
		} catch (NumberFormatException e) {
			// TODO
			return null;
		}
	}
}
