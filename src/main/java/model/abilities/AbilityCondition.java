package model.abilities;

import model.entities.Player;

public interface AbilityCondition {

	public boolean isConditionFulfilled(Player player);
	
}
