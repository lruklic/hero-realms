package model.cards.implementation;

import java.util.List;

import model.cards.Ability;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;

/**
 * 
 * Class which represents champions, cards that can be put into battlefield
 * 
 * @author Ivan
 *
 */
public class Champion extends CardImplementation {

	private boolean tapped;
	
	public Champion(List<Ability> abilities, Faction faction, int cost, String name) {
		super(abilities, faction, cost, name);
		tapped = false;
	}

	@Override
	public void goIntoPlay(Player player) {
		tapped = false;
		player.getBoard().add(this);
	}

	@Override
	public void activate(Player player, AbilityTrigger trigger) {
		if(trigger.equals(AbilityTrigger.TAP)) {
			if(tapped) {
				//TODO cannot be tapped again
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
}
