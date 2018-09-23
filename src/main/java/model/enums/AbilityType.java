package model.enums;

import java.util.Arrays;
import java.util.List;

import model.abilities.AbilityArgument;
import model.entities.Targetable;

public enum AbilityType {

	DEAL_DAMAGE("DAMAGE") {
		@Override
		public void activate(Targetable target, AbilityArgument argument) {
			if (!target.isSubtype(TargetSubtype.HAS_HEALTH)) {
				throw new IllegalArgumentException("Illegal selection, target has no health!");
			}
			int damage = Integer.parseInt(argument.getArgument("DAMAGE"));
			target.put(Targetable.CURRENT_HEALTH, target.get(Targetable.CURRENT_HEALTH) - damage);
			target.alert(TargetSubtype.HAS_HEALTH);
		}
	},
	PAY_MANA("MANA") {
		@Override
		public void activate(Targetable target, AbilityArgument argument) {
			if (!target.isSubtype(TargetSubtype.HAS_MANA)) {
				throw new IllegalArgumentException("Illegal selection, target has no mana!");
			}
			int mana = Integer.parseInt(argument.getArgument("MANA"));
			int targetMana = target.get("CURRENT_MANA");
			if (targetMana < mana) {
				throw new IllegalArgumentException("Not enough mana!");
			}
			target.put("CURRENT_MANA", targetMana - mana);
			target.alert(TargetSubtype.HAS_MANA);
		}
	};

	private List<String> arguments;

	private AbilityType(String... arguments) {
		this.arguments = Arrays.asList(arguments);
	}

	public List<String> getArguments() {
		return arguments;
	}

	public abstract void activate(Targetable target, AbilityArgument argument);
}
