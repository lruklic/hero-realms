package model.cards;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Deck implementation
 * 
 * @author lruklic
 *
 */

public class DeckImpl implements Deck {

	private Stack<Card> cards;
	
	@Override
	public Card drawCard() {
		return cards.pop();
	}

	@Override
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}

	@Override
	public void putCardOnTop(Card card) {
		cards.push(card);
	}

	@Override
	public int cardsRemaining() {
		return cards.size();
	}

}
