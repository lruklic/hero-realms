package model.cards;

import java.util.List;

import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;

/**
 * 
 * Interface which defines the most basic concept of a card. Contains properties possessed by most cards,
 * like cost, faction and the ability to be played
 * 
 * @author Ivan
 *
 */
public interface Card {

	public String getName();
	
	public int getCost();
	
	public Faction getFaction();
	
	public void goIntoPlay(Player player);
	
	public void activate(Player player, AbilityTrigger trigger);
	
	public List<Ability> getAbilities();
}
