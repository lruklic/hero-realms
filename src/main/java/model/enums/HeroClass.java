package model.enums;

import model.cards.Deck;

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
			// TODO Auto-generated method stub
			return null;
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
