package model.abilities;

import model.entities.Targetable;
import model.enums.AbilityTrigger;

public interface Ability extends Targetable {

	public void trigger(Targetable target, AbilityTrigger abilityTrigger);

	@Override
	public Ability copy();
}
