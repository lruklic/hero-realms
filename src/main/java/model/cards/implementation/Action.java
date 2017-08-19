package model.cards.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;
import model.enums.HeroClass;

public class Action extends CardImplementation {

	public Action(List<Ability> abilities, Faction faction, int cost, String name, String code, String description,
			HeroClass heroClass) {
		super(abilities, faction, cost, name, code, description, heroClass);
	}

	@Override
	public void goIntoPlay(Player player) {
		activate(player, AbilityTrigger.DEFAULT);
		player.getActions().add(this);
	}

}
