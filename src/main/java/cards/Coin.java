package cards;

import entities.Player;
import enums.Faction;

/**
 * 
 * Coin is a simple card which cannot be bought, has no faction and simply increases player's gold
 * by one when played
 * 
 * @author Ivan
 *
 */
public class Coin implements Card {

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
		player.increaseGold(1);
	}

}
