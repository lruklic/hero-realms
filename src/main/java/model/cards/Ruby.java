package model.cards;

import model.entities.Player;
import model.enums.Faction;

/**
 * 
 * Ruby is a simple card which cannot be bought, has no faction and simply increases player's gold
 * by two when played
 * 
 * @author Ivan
 *
 */
public class Ruby implements Card {

	@Override
	public int getCost() {
		return 0;
	}

	@Override
	public Faction getFaction() {
		return Faction.NONE;
	}

	@Override
	public void goIntoPlay(Player player) {
		player.increaseGold(2);
	}

}