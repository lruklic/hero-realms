package model.cards;

/**
 * 
 * Utility interface which defines a deck, container for cards
 * 
 * @author Ivan
 *
 */
public interface Deck {

	public Card drawACard();
	
	public void shuffle();
	
	public void putCardOnTop(Card card);
}
