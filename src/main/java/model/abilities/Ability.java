package model.abilities;

import model.entities.Player;
import model.enums.AbilityTrigger;

public interface Ability {

	public void activate(Player player);
	
	public AbilityTrigger getTrigger();
	
}
