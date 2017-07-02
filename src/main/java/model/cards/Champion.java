package model.cards;

import model.entities.Player;
import model.enums.Faction;

/**
 * 
 * Class which represents champions, cards that can be put into battlefield
 * 
 * @author Ivan
 *
 */
public class Champion implements Card {

	private Faction faction;
	
	private int cost;
	
	private boolean tapped;
	
	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public Faction getFaction() {
		return faction;
	}

	@Override
	public void goIntoPlay(Player player) {
		player.getBoard().add(this);
	}
}
