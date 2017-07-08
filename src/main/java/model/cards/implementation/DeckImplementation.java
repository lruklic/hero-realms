package model.cards.implementation;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import model.cards.Card;
import model.cards.Deck;

/**
 * Deck implementation
 * 
 * @author lruklic
 *
 */

public class DeckImplementation implements Deck {

	private Stack<Card> cards;
	
	public DeckImplementation(List<Card> cards) {
		this.cards = new Stack<>();
		for(Card card : cards) {
			this.cards.push(card);
		}
	}
	
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
	
	@Override
	public boolean isEmpty() {
		return cards.empty();
	}
	
	@Override
	public void fillWithCards(List<Card> cards) {
		for(Card card : cards) {
			this.cards.push(card);
		}
		shuffle();
	}
}
