package model.cards.implementation;

import java.util.List;

import model.cards.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;

public class Item extends CardImplementation {

	public Item(List<Ability> abilities, Faction faction, int cost, String name) {
		super(abilities, faction, cost, name);
	}

	@Override
	public void goIntoPlay(Player player) {
		activate(player, AbilityTrigger.DEFAULT);
		player.getActions().add(this);
	}

}
