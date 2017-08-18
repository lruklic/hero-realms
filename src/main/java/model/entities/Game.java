package model.entities;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;

/**
 * 
 * Interface which represents a game
 * 
 * @author Ivan
 *
 */
public interface Game {

	public Deck getGameDeck();

	public void updateMarket();

	public List<Card> getMarket();

	public Player getCurrentPlayer();

	public List<Player> getPlayers();
}
