package model.cards.implementation;

import model.cards.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.AbilityType;

public class AbilityImplementation implements Ability {

	private AbilityTrigger trigger;
	
	private AbilityType type;

	@Override
	public void activate(Player player) {
		type.activate(player);
	}

	@Override
	public AbilityTrigger getTrigger() {
		return trigger;
	}
}
