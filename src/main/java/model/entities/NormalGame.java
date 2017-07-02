package model.entities;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;

/**
 * 
 * This class represents a normal, 50 health, one on one game
 * 
 * @author Ivan
 *
 */
public class NormalGame implements Game {

	private Deck deck;
	
	private List<Card> market;
	
	private Player currentPlayer;
	
	@Override
	public Deck getGameDeck() {
		return deck;
	}

	@Override
	public void updateMarket() {
		market.add(deck.drawCard());
	}

	@Override
	public List<Card> getMarket() {
		return market;
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

}
