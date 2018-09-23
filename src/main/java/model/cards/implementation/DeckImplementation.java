package model.cards.implementation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import model.cards.Card;
import model.cards.Deck;
import model.entities.implementation.TargetableImplementation;
import model.enums.TargetSubtype;

/**
 * Deck implementation
 * 
 * @author lruklic
 *
 */

public class DeckImplementation extends TargetableImplementation implements Deck {

	private Stack<Card> cards;

	protected DeckImplementation(DeckImplementation deck) {
		super(deck);
		this.cards = new Stack<>();
		Stack<Card> reverseStack = new Stack<>();
		deck.cards.forEach(card -> reverseStack.push(card.copy()));
		reverseStack.forEach(card -> this.cards.push(card));
	}

	public DeckImplementation(List<Card> cards) {
		this.cards = new Stack<>();
		for (Card card : cards) {
			this.cards.push(card);
		}
		shuffle();
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
	public void fillWithCards(Collection<Card> cards) {
		cards.forEach(card -> this.cards.push(card));
		shuffle();
	}

	@Override
	public void alert(TargetSubtype subtype) {
	}

	@Override
	public void takeMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public Deck copy() {
		return new DeckImplementation(this);
	}
}
