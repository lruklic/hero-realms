package model.cards.implementation;

import java.util.List;

import model.abilities.Ability;
import model.enums.Faction;
import model.enums.TargetSubtype;

public class Spell extends CardImplementation {

	protected Spell(Spell spell) {
		super(spell);
	}

	public Spell(List<Ability> abilities, Faction faction, int cost, String name, String code, String description) {
		super(abilities, faction, cost, name, code, description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void takeMessage(String message) {
	}

	@Override
	public void alert(TargetSubtype subtype) {
	}

	@Override
	public Spell copy() {
		return new Spell(this);
	}
}
