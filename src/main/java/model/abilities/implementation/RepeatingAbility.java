package model.abilities.implementation;

import model.abilities.Ability;
import model.abilities.AbilityCounter;
import model.entities.Player;
import model.enums.AbilityTrigger;

public class RepeatingAbility implements Ability {

	private Ability ability;
	
	private AbilityCounter counter;
	
	public RepeatingAbility(Ability ability, AbilityCounter counter) {
		super();
		this.ability = ability;
		this.counter = counter;
	}

	@Override
	public void activate(Player player) {
		int count = counter.getNumberOfOccurences(player);
		for(int i = 0; i < count; i++) {
			ability.activate(player);
		}
	}

	@Override
	public AbilityTrigger getTrigger() {
		return ability.getTrigger();
	}

	
	
}
