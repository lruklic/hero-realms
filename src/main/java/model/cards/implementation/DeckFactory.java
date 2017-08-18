package model.cards.implementation;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;

public class DeckFactory {

	private DeckFactory() {

	}

	public static Deck createDeckWithCards(List<Card> cards) {
		return new DeckImplementation(cards);
	}
}
