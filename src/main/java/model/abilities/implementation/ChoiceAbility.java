package model.abilities.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Option;
import model.entities.Player;
import model.entities.implementation.OptionFactory;
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
		Option<Ability> option = player.pickAnOption(OptionFactory.getOptions(abilities));
		option.getStoredEntity().activate(player);
	}

	@Override
	public AbilityTrigger getTrigger() {
		return abilities.get(0).getTrigger();
	}
}
