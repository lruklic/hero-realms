package model.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.DeckFactory;
import utils.JsonUtils;

/**
 * Unique player class that defines starting deck and hero power.
 * 
 * @author lruklic
 *
 */

public enum HeroClass {

	CLERIC(55) {
		@Override
		public Deck getDeck() {
			return null;
		}
	},

	FIGHTER(60) {
		@Override
		public Deck getDeck() {
			// TODO Auto-generated method stub
			return null;
		}
	},

	RANGER(58) {
		@Override
		public Deck getDeck() {
			// TODO Auto-generated method stub
			return null;
		}
	},

	THIEF(52) {
		@Override
		public Deck getDeck() {
			// TODO Auto-generated method stub
			return null;
		}
	},

	WIZARD(50) {
		@Override
		public Deck getDeck() {
			// TODO Auto-generated method stub
			return null;
		}
	},

	NONE(50) {
		@Override
		public Deck getDeck() {
			List<Card> cards = new ArrayList<>();
			int amountOfGoldInNormalDeck = 7;
			cards.addAll(Collections.nCopies(amountOfGoldInNormalDeck, JsonUtils.getCardByName("Gold")));
			cards.add(JsonUtils.getCardByName("Ruby"));
			cards.add(JsonUtils.getCardByName("Dagger"));
			cards.add(JsonUtils.getCardByName("Shortsword"));
			return DeckFactory.createDeckWithCards(cards);
		}
	};

	private int health;

	private HeroClass(int health) {
		this.health = health;
	}

	public int getHealth() {
		return this.health;
	}

	public abstract Deck getDeck();
}
