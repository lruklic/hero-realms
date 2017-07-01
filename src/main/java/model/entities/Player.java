package model.entities;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;

/**
 * 
 * Interface which defines a player, listing all the possible actions that a player can take
 * 
 * @author Ivan
 *
 */
public interface Player {

	public void draw();
	
	public void discard();
	
	public List<Card> getHand();

	public Deck getDeck();
	
	public List<Card> getDiscardPile();
	
	public void startTurn();
	
	public void endTurn();
	
	public void increaseGold(int number);
}
