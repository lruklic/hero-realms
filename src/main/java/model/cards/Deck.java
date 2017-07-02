package model.cards;

import java.util.List;

/**
 * 
 * Utility interface which defines a deck, container for cards
 * 
 * @author Ivan
 *
 */
public interface Deck {

	public Card drawCard();
	
	public void shuffle();
	
	public void putCardOnTop(Card card);
	
	public int cardsRemaining();
	
	public boolean isEmpty();
	
	public void fillWithCards(List<Card> cards);
}
