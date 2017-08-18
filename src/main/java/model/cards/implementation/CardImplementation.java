package model.cards.implementation;

import java.util.List;

import model.abilities.Ability;
import model.cards.Card;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;

public abstract class CardImplementation implements Card {

	private static int currentId = 0;

	private List<Ability> abilities;

	private Faction faction;

	private int cost;

	private String name;

	private int id;

	public CardImplementation(List<Ability> abilities, Faction faction, int cost, String name) {
		this.abilities = abilities;
		this.faction = faction;
		this.cost = cost;
		this.name = name;
		this.id = getUniqueCardId();
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public Faction getFaction() {
		return faction;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void activate(Player player, AbilityTrigger trigger) {
		for (Ability ability : abilities) {
			if (ability.getTrigger().equals(trigger)) {
				ability.activate(player);
			}
		}
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public int getId() {
		return id;
	}

	private static int getUniqueCardId() {
		return currentId++;
	}
}
