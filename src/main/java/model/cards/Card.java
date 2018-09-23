package model.cards;

import java.util.List;

import model.abilities.Ability;
import model.entities.Player;
import model.entities.Targetable;
import model.enums.Faction;

/**
 * 
 * Interface which defines the most basic concept of a card. Contains properties
 * possessed by most cards, like cost, faction and the ability to be played
 * 
 * @author Ivan
 *
 */
public interface Card extends Targetable {

	public static final String SUPPLY_COST = "SUPPLY_COST";

	public String getName();

	public int getCost();

	public Faction getFaction();

	public String getCode();

	public String getDescription();

	public List<Ability> getAbilities();

	public void goIntoPlay(Player player);

	@Override
	public Card copy();
}
