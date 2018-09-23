package model.cards;

import java.util.Collection;

import model.entities.Targetable;

/**
 * 
 * Utility interface which defines a deck, container for cards
 * 
 * @author Ivan
 *
 */
public interface Deck extends Targetable {

	public Card drawCard();

	public void shuffle();

	public void putCardOnTop(Card card);

	public int cardsRemaining();

	public boolean isEmpty();

	public void fillWithCards(Collection<Card> cards);

	@Override
	public Deck copy();
}
