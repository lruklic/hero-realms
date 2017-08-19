package model.cards.implementation;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;
import model.enums.HeroClass;

/**
 * 
 * Class which represents champions, cards that can be put into battlefield
 * 
 * @author Ivan
 *
 */
public class Champion extends CardImplementation {

	private boolean tapped;

	private int health;

	private int currentHealth;

	private boolean isGuard;

	public Champion(List<Ability> abilities, Faction faction, int cost, String name, String code, String description,
			HeroClass heroClass, boolean isGuard, int health) {
		super(abilities, faction, cost, name, code, description, heroClass);
		this.health = health;
		this.isGuard = isGuard;
		this.tapped = false;
	}

	@Override
	public void goIntoPlay(Player player) {
		tapped = false;
		player.getBoard().add(this);
	}

	@Override
	public void activate(Player player, AbilityTrigger trigger) {
		if (trigger.equals(AbilityTrigger.TAP)) {
			if (tapped) {
				// TODO cannot be tapped again
			} else {
				tapped = true;
			}
		}
		super.activate(player, trigger);
	}

	public boolean isTapped() {
		return tapped;
	}

	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	public int getHealth() {
		return health;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public boolean isGuard() {
		return isGuard;
	}
}
