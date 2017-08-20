package model.entities.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.CardImplementation;
import model.cards.implementation.DeckFactory;
import model.entities.Game;
import model.entities.Player;
import model.enums.AbilityTrigger;
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

	private static final int MARKET_SIZE = 6;

	private Deck deck;

	private List<Card> market;

	private Player currentPlayer;

	private Map<String, Player> players;

	public NormalGame(String firstUsername, String secondUsername) {
		if (firstUsername.equals(secondUsername)) {
			throw new IllegalArgumentException("Player names cannot be the same!");
		}
		Player firstPlayer = new PlayerImplementation(HeroClass.NONE, firstUsername, secondUsername);
		Player secondPlayer = new PlayerImplementation(HeroClass.NONE, secondUsername, firstUsername);
		currentPlayer = firstPlayer;
		players = new HashMap<>();
		players.put(firstUsername, firstPlayer);
		players.put(secondUsername, secondPlayer);
		deck = DeckFactory.createDeckWithCards(JsonUtils.createGameDeck());
		market = new ArrayList<>();
		for (int i = 0; i < MARKET_SIZE; i++) {
			updateMarket();
		}
		updateMarket();
	}

	@Override
	public Deck getGameDeck() {
		return deck;
	}

	@Override
	public void updateMarket() {
		if (market.size() == MARKET_SIZE - 1 && market.stream().filter(card -> card.getCode().equals("FIREGEM"))
				.collect(Collectors.toList()).isEmpty()) {
			market.add(JsonUtils.getCardByName("Fire Gem"));
		} else {
			market.add(deck.drawCard());
		}
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
	public Map<String, Player> getPlayers() {
		return players;
	}

	@Override
	public void start() {
		currentPlayer.startTurn();
	}

	@Override
	public void performAction(String userName, String action, int cardId) {
		Player player = players.get(userName);
		Card card = CardImplementation.getCardById(cardId);
		// TODO check for validity
		switch (action) {
		case "PLAY":
			player.play(card);
			break;
		case "BUY":
			player.buy(card);
			market.remove(card);
			updateMarket();
			break;
		case "TAP":
		case "FACTION":
		case "SACRIFICE":
			player.activate(card, AbilityTrigger.valueOf(action));
			break;
		case "DISCARD":
			player.discard(card);
			break;
		case "TRASH":
			player.trash(card);
			break;
		case "END":
			player.endTurn();
			currentPlayer = players.get(player.getNextPlayer());
			currentPlayer.startTurn();
			break;
		default:
			throw new IllegalArgumentException("Illegal action specified!");
		}
	}
}
