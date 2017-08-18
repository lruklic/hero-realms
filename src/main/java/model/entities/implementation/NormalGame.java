package model.entities.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.DeckImplementation;
import model.entities.Game;
import model.entities.Player;
import model.enums.HeroClass;
import utils.JsonUtils;

/**
 * 
 * This class represents a normal, 50 health, one on one game
 * 
 * @author Ivan
 *
 */
public class NormalGame implements Game {

	private static final int MARKET_SIZE = 5;

	private Deck deck;

	private List<Card> market;

	private Player currentPlayer;

	private List<Player> players;

	public NormalGame(String firstUsername, String secondUsername) {
		if (firstUsername.equals(secondUsername)) {
			throw new IllegalArgumentException("Player names cannot be the same!");
		}
		Player firstPlayer = new PlayerImplementation(HeroClass.NONE, firstUsername);
		Player secondPlayer = new PlayerImplementation(HeroClass.NONE, secondUsername);
		currentPlayer = firstPlayer;
		players = Arrays.asList(firstPlayer, secondPlayer);
		deck = new DeckImplementation(JsonUtils.createGameDeck());
		market = new ArrayList<>();
		for (int i = 0; i < MARKET_SIZE; i++) {
			updateMarket();
		}
	}

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

	@Override
	public List<Player> getPlayers() {
		return players;
	}

}
