package model.cards.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.Faction;
import model.enums.TargetSubtype;

public class Creature extends CardImplementation {

	protected Creature(Creature creature) {
		super(creature);
	}

	public Creature(List<Ability> abilities, Faction faction, int cost, String name, String code, String description,
			int health) {
		super(abilities, faction, cost, name, code, description);
		put(CURRENT_HEALTH, health);
	}

	@Override
	public void goIntoPlay(Player player) {

	}

	@Override
	public void alert(TargetSubtype subtype) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public Creature copy() {
		return new Creature(this);
	}
}
