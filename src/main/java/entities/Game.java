package entities;

import java.util.List;

import cards.Card;
import cards.Deck;

/**
 * 
 * Interface which represents a game
 * 
 * @author Ivan
 *
 */
public interface Game {

	public Deck getGameDeck();
	
	public void updateMarket();
	
	public List<Card> getMarket(); 
	
	public Player getCurrentPlayer();
	
	
}
