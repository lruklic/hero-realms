package model.entities;

import java.util.ArrayList;
import java.util.List;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Action;
import model.cards.implementation.Champion;
import model.enums.HeroClass;

/**
 * Implementation of player.
 * 
 * @author lruklic
 *
 */

public class PlayerImplementation implements Player {

	private String name;

	private HeroClass heroClass;
	/**
	 * Health left. Initial value if defined by hero class.
	 */
	private int health;
	
	private int damage;

	private int gold;
	
	private Deck deck;
	
	private List<Card> discardPile;
	
	private List<Card> hand;
	
	private List<Champion> board;
	
	private List<Card> actions;
	
	private static final int NORMAL_NUMBER_OF_CARDS_IN_HAND = 5;
	
	public PlayerImplementation() {
		this(HeroClass.NONE);
	}
	
	public PlayerImplementation(HeroClass heroClass) {
		this.heroClass = heroClass;
		this.health = this.heroClass.getHealth();
		this.deck = getDeckForClass(this.heroClass);
		this.deck.shuffle();
		this.gold = 0;
		this.damage = 0;
		this.board = new ArrayList<>();
		this.discardPile = new ArrayList<>();
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
	}

	@Override
	public void draw() {
		if(deck.isEmpty()) {
			deck.fillWithCards(discardPile);
		}
		hand.add(deck.drawCard());
	}

	@Override
	public void discard(Card card) {
		hand.remove(card);
		discardPile.add(card);
	}
	
	@Override
	public void play(Card card) {
		hand.remove(card);
		card.goIntoPlay(this);
	}
	
	@Override
	public void buy(Card card) {
		if (gold < card.getCost()) {
			//TODO can't buy this
		} else {
			gold -= card.getCost();
			discardPile.add(card);
		}
	}

	@Override
	public List<Card> getHand() {
		return hand;
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public List<Card> getDiscardPile() {
		return discardPile;
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endTurn() {
		for(Card card : actions) {
			//TODO if not a champion, go to discard pile
			discardPile.add(card);
		}
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
		for(Champion champion : board) {
			champion.setTapped(false);
		}
	}

	@Override
	public void increaseGold(int number) {
		gold += number;
	}
	
	@Override
	public void increaseDamage(int number) {
		damage += number;
	}
	
	@Override
	public void increaseHealth(int number) {
		health += number;
	}
	
	private void drawAHand(int numberOfCards) {
		this.hand = new ArrayList<>();
		for(int i = 0; i < numberOfCards; i++) {
			draw();
		}
	}

	//TODO decide where to put this
	private static Deck getDeckForClass(HeroClass heroClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Champion> getBoard() {
		return board;
	}

	@Override
	public List<Card> getActions() {
		return actions;
	}
	
	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public int getGold() {
		return gold;
	}
}
