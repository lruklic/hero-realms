package model.abilities.implementation;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;

public class SimpleAbility implements Ability {

	private AbilityType type;

	private AbilityTrigger trigger;	
	
	public SimpleAbility(AbilityType type, AbilityTrigger trigger) {
		super();
		this.type = type;
		this.trigger = trigger;
	}

	@Override
	public void activate(Player player) {
		type.activate(player);
	}

	@Override
	public AbilityTrigger getTrigger() {
		return trigger;
	}
}
