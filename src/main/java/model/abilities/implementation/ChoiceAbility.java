package model.abilities.implementation;

import java.util.ArrayList;
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
		if(abilities.isEmpty()) {
			throw new IllegalArgumentException("There must be at least one option.");
		}
		this.abilities = abilities;
	}

	@Override
	public void activate(Player player) {
		List<Option> options = new ArrayList<>();
		for(Ability ability : abilities) {
			options.add(OptionFactory.getAbilityOption(ability));
		}
		Option option = player.pickAnOption(options);
		for(Ability ability : abilities) {
			if(ability.equals(option.getStoredEntity())) {
				ability.activate(player);
			}
		}
	}

	@Override
	public AbilityTrigger getTrigger() {
		return abilities.get(0).getTrigger();
	}
}
