package model.abilities.implementation;

import model.abilities.Ability;
import model.abilities.AbilityCondition;
import model.entities.Player;
import model.enums.AbilityTrigger;

public class ConditionalAbility implements Ability {

	private AbilityCondition condition;

	private Ability fulfilledAbility;

	private Ability unfulfilledAbility;

	public ConditionalAbility(Ability fulfilledAbility, Ability unfulfilledAbility, AbilityCondition condition) {
		super();
		this.condition = condition;
		this.fulfilledAbility = fulfilledAbility;
		this.unfulfilledAbility = unfulfilledAbility;
	}

	@Override
	public void activate(Player player) {
		if (condition.isConditionFulfilled(player)) {
			fulfilledAbility.activate(player);
		} else {
			unfulfilledAbility.activate(player);
		}
	}

	@Override
	public AbilityTrigger getTrigger() {
		return fulfilledAbility.getTrigger();
	}

}
