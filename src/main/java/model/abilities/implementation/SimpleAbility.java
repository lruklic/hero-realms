package model.abilities.implementation;

import java.util.Map;

import model.abilities.Ability;
import model.abilities.AbilityArgument;
import model.entities.Targetable;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;

public class SimpleAbility extends AbilityImplementation {

	private AbilityType type;

	private AbilityArgument argument;

	protected SimpleAbility(SimpleAbility ability) {
		super(ability);
		this.type = ability.type;
		this.argument = ability.argument;
	}

	public SimpleAbility(AbilityType type, Map<String, Integer> arguments) {
		super();
		this.type = type;
		arguments.forEach((K, V) -> put(K, V));
		this.argument = AbilityArgumentFactory.getArgument(this, type);
	}

	@Override
	public void trigger(Targetable target, AbilityTrigger abilityTrigger) {
		type.activate(target, argument);
	}

	@Override
	public Ability copy() {
		return new SimpleAbility(this);
	}
}
