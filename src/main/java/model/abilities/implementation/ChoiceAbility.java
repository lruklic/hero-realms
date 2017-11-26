package model.abilities.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;

public class ChoiceAbility implements Ability {

	private List<Ability> abilities;

	public ChoiceAbility(List<Ability> abilities) {
		super();
		if (abilities.isEmpty()) {
			throw new IllegalArgumentException("There must be at least one option.");
		}
		this.abilities = abilities;
	}

	@Override
	public void activate(Player player) {
		// TODO verify that this works
		player.setQuery("TARGET CHOICE");
	}

	@Override
	public AbilityTrigger getTrigger() {
		return abilities.get(0).getTrigger();
	}
}
