package model.entities;

import java.util.ArrayList;
import java.util.List;

import model.cards.Card;
import model.cards.Champion;
import model.cards.Deck;
import model.enums.HeroClass;

/**
 * Implementation of player.
 * 
 * @author lruklic
 *
 */

public class PlayerImpl implements Player {

	private String name;

	private HeroClass heroClass;
	/**
	 * Health left. Initial value if defined by hero class.
	 */
	private int health;
	
	private Deck deck;
	
	private List<Card> discardPile;
	
	private List<Card> hand;
	
	private List<Champion> board;
	
	private int gold;
	
	private static final int NORMAL_NUMBER_OF_CARDS_IN_HAND = 5;
	
	public PlayerImpl() {
		this(HeroClass.NONE);
	}
	
	public PlayerImpl(HeroClass heroClass) {
		this.heroClass = heroClass;
		this.health = this.heroClass.getHealth();
		this.deck = getDeckForClass(this.heroClass);
		this.deck.shuffle();
		this.gold = 0;
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
	public void discard() {
		// TODO Auto-generated method stub
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
		for(Card card : hand) {
			//TODO if not a champion, go to discard pile
			discardPile.add(card);
		}
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
		//TODO prepare all champions
	}

	@Override
	public void increaseGold(int number) {
		gold += number;
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
}
