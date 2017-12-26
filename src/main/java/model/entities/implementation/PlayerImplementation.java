package model.entities.implementation;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidUserActionException;
import model.abilities.BuyModifier;
import model.abilities.implementation.DefaultBuyModifier;
import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Champion;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.HeroClass;

/**
 * Implementation of player.
 * 
 * @author lruklic
 *
 */

public class PlayerImplementation implements Player {

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

	private String userName;

	private String nextPlayer;

	private BuyModifier buyModifier;

	private String query;

	private static final int NORMAL_NUMBER_OF_CARDS_IN_HAND = 5;

	public PlayerImplementation(HeroClass heroClass, String userName, String nextPlayer) {
		this.heroClass = heroClass;
		this.health = heroClass.getHealth();
		this.deck = heroClass.getDeck();
		this.gold = 0;
		this.damage = 0;
		this.board = new ArrayList<>();
		this.actions = new ArrayList<>();
		this.discardPile = new ArrayList<>();
		this.userName = userName;
		this.nextPlayer = nextPlayer;
		this.hand = new ArrayList<>();
		this.buyModifier = new DefaultBuyModifier(this);
		this.query = "";
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
	}

	@Override
	public void draw() {
		if (deck.isEmpty()) {
			deck.fillWithCards(discardPile);
			discardPile.clear();
		}
		hand.add(deck.drawCard());
	}

	@Override
	public void discard(Card card) {
		if (!query.equals("DISCARD")) {
			throw new InvalidUserActionException("Invalid state encountered, DISCARD query was not issued!");
		} else if (!hand.contains(card)) {
			throw new InvalidUserActionException("Select a card in your hand to discard!");
		} else {
			hand.remove(card);
			discardPile.add(card);
		}
	}

	@Override
	public void play(Card card) {
		if (!hand.contains(card)) {
			throw new InvalidUserActionException("That card is not in your hand?!");
		} else {
			hand.remove(card);
			card.goIntoPlay(this);
		}
	}

	@Override
	public void buy(Card card) {
		if (gold < card.getCost()) {
			throw new InvalidUserActionException("Not enough gold to buy that card!");
		} else {
			gold -= card.getCost();
			buyModifier.apply(card);
		}
	}

	@Override
	public void sacrifice(Card card) {
		if (!query.equals("SACRIFICE")) {
			throw new InvalidUserActionException("Invalid state encountered, SACRIFICE query was not issued!");
		} else if (!hand.contains(card) && !discardPile.contains(card)) {
			throw new InvalidUserActionException("Select a card in your hand or discard pile to sacrifice!");
		} else {
			hand.remove(card);
			discardPile.remove(card);
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
		query = "";
	}

	@Override
	public void endTurn() {
		discardPile.addAll(actions);
		discardPile.addAll(hand);
		actions.clear();
		hand.clear();
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
		for (Champion champion : board) {
			champion.prepare();
		}
		buyModifier = new DefaultBuyModifier(this);
		query = "";
		gold = 0;
		damage = 0;
	}

	@Override
	public void activate(Card card, AbilityTrigger trigger) {
		card.activate(this, trigger);
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
		hand.clear();
		for (int i = 0; i < numberOfCards; i++) {
			draw();
		}
	}

	@Override
	public void stunChampion(Champion champion) {
		if (!query.equals("STUN")) {
			throw new InvalidUserActionException("Invalid state encountered, STUN query was not issued!");
		} else if (!board.contains(champion)) {
			throw new InvalidUserActionException("That champion does not exist!");
		} else {
			board.remove(champion);
			discardPile.add(champion);
		}
	}

	@Override
	public void prepareChampion(Champion champion) {
		if (!query.equals("PREPARE")) {
			throw new InvalidUserActionException("Invalid state encountered, PREPARE query was not issued!");
		} else if (!board.contains(champion)) {
			throw new InvalidUserActionException("That champion does not exist!");
		} else {
			champion.prepare();
		}
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

	@Override
	public String getName() {
		return userName;
	}

	@Override
	public String getNextPlayer() {
		return nextPlayer;
	}

	@Override
	public HeroClass getHeroClass() {
		return heroClass;
	}

	@Override
	public void setBuyModifier(BuyModifier buyModifier) {
		this.buyModifier = buyModifier;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void sendCardToTop(Card card, Class<? extends Card> selectionCriteria) {
		String expectedQuery = selectionCriteria.getName().toUpperCase() + "_TO_TOP";
		if (!query.equals(expectedQuery)) {
			throw new InvalidUserActionException(
					"Invalid state encountered, " + expectedQuery + " query was not issued!");
		} else if (!discardPile.contains(card)) {
			throw new InvalidUserActionException("You must select a card in your discard pile!");
		} else if (!selectionCriteria.isInstance(card)) {
			throw new InvalidUserActionException("Please select a " + selectionCriteria.getName() + "!");
		} else {
			discardPile.remove(card);
			deck.putCardOnTop(card);
		}
	}
}
