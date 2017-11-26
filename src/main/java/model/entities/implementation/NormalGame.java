package model.entities.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.InvalidUserActionException;
import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.CardImplementation;
import model.cards.implementation.Champion;
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
		for (int i = 0; i < MARKET_SIZE - 1; i++) {
			market.add(deck.drawCard());
		}
		market.add(JsonUtils.getCardByCode("FIRGEM"));
	}

	@Override
	public Deck getGameDeck() {
		return deck;
	}

	@Override
	public void removeCardFromMarket(Card card) {
		int index = market.indexOf(card);
		if (card.getCode().equals("FIRGEM")) {
			market.set(index, JsonUtils.getCardByCode("FIRGEM"));
		} else {
			market.set(index, deck.drawCard());
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
		if (!player.equals(getCurrentPlayer())) {
			throw new InvalidUserActionException("It is not your turn!");
		}
		// TODO check for validity
		switch (action) {
		case "PLAY":
			player.play(card);
			break;
		case "BUY":
			player.buy(card);
			removeCardFromMarket(card);
			break;
		case "TAP":
		case "FACTION":
			player.activate(card, AbilityTrigger.valueOf(action));
			break;
		case "TRASH":
			player.activate(card, AbilityTrigger.valueOf(action));
			player.getActions().remove(card);
			break;
		case "DISCARD":
			player.discard(card);
			break;
		case "SACRIFICE":
			player.trash(card);
			break;
		case "END":
			player.endTurn();
			currentPlayer = players.get(player.getNextPlayer());
			currentPlayer.startTurn();
			break;
		case "DAMAGE":
			// TODO improve, current implementation is sloppy
			Player targetPlayer = players.values().stream().filter(p -> !p.equals(player)).findFirst().get();
			if (card != null) {
				Champion champion = (Champion) card;
				if (champion.getCurrentHealth() <= player.getDamage()) {
					targetPlayer.stunChampion(champion);
				}
				player.increaseDamage(-champion.getCurrentHealth());
			} else {
				targetPlayer.increaseHealth(-player.getDamage());
				player.increaseDamage(-player.getDamage());
			}
			break;
		case "PREPARE":
			((Champion) card).setTapped(false);
			break;
		case "STUN":
			for (Player championPlayer : players.values()) {
				if (championPlayer.getBoard().contains(card)) {
					championPlayer.stunChampion((Champion) card);
					break;
				}
			}
			break;
		default:
			throw new InvalidUserActionException("Illegal action specified!");
		}
	}
}
