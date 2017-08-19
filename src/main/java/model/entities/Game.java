package model.entities;

import java.util.List;
import java.util.Map;

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

	public Map<String, Player> getPlayers();

	public void performAction(String userName, String action, int cardId);

	public void start();
}
