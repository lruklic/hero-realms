package cards;

import entities.Player;
import enums.Faction;

/**
 * 
 * Interface which defines the most basic concept of a card. Contains properties possessed by most cards,
 * like cost, faction and the ability to be played
 * 
 * @author Ivan
 *
 */
public interface Card {

	public int getCost();
	
	public Faction getFaction();
	
	public void goIntoPlay(Player player);
	
}
