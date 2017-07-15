package model.abilities.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;

public class CompositeAbility implements Ability {

	private List<Ability> abilities;
	
	public CompositeAbility(List<Ability> abilities) {
		super();
		if(abilities.isEmpty()) {
			throw new IllegalArgumentException("There must be at least one ability.");
		}
		this.abilities = abilities;
	}

	@Override
	public void activate(Player player) {
		for(Ability ability : abilities) {
			ability.activate(player);
		}
	}

	@Override
	public AbilityTrigger getTrigger() {
		return abilities.get(0).getTrigger();
	}

}
