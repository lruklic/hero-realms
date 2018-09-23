package model.abilities.implementation;

import model.abilities.Ability;
import model.entities.Targetable;
import model.enums.AbilityTrigger;

public final class EmptyAbility extends AbilityImplementation {

	private static EmptyAbility instance = new EmptyAbility();

	private EmptyAbility() {

	}

	public static EmptyAbility getInstance() {
		return instance;
	}

	@Override
	public void trigger(Targetable target, AbilityTrigger trigger) {

	}

	@Override
	public Ability copy() {
		return instance;
	}
}
