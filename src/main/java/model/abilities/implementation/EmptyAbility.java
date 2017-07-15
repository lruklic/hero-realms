package model.abilities.implementation;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;

public final class EmptyAbility implements Ability {

	private static EmptyAbility instance = new EmptyAbility();
	
	public static EmptyAbility getInstance() {
		return instance;
	}
	
	@Override
	public void activate(Player player) {
		
	}
	
	@Override
	public AbilityTrigger getTrigger() {
		throw new UnsupportedOperationException("Empty abilities have no trigger");
	}
}
