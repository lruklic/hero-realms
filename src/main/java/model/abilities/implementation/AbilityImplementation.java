package model.abilities.implementation;

import model.abilities.Ability;
import model.entities.implementation.TargetableImplementation;
import model.enums.TargetSubtype;

public abstract class AbilityImplementation extends TargetableImplementation implements Ability {

	protected AbilityImplementation() {
	}

	protected AbilityImplementation(AbilityImplementation ability) {
		super(ability);
	}

	@Override
	public void alert(TargetSubtype subtype) {

	}

	@Override
	public void takeMessage(String message) {

	}
}
