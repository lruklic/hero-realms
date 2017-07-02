package model.entities;

import java.util.List;

import model.cards.Card;
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
	
	private List<Card> board;
	
	@Override
	public void draw() {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void increaseGold(int number) {
		// TODO Auto-generated method stub
		
	}
	
}
