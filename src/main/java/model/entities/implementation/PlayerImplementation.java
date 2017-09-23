package model.entities.implementation;

import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidUserActionException;
import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Champion;
import model.entities.Option;
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
			throw new InvalidUserActionException("Not enough gold to buy that card!");
		} else {
			gold -= card.getCost();
			discardPile.add(card);
		}
	}

	@Override
	public void trash(Card card) {
		hand.remove(card);
		discardPile.remove(card);
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
		discardPile.addAll(actions);
		discardPile.addAll(hand);
		actions.clear();
		hand.clear();
		drawAHand(NORMAL_NUMBER_OF_CARDS_IN_HAND);
		for (Champion champion : board) {
			champion.setTapped(false);
		}
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
		if (!board.contains(champion)) {
			throw new InvalidUserActionException("That champion does not exist!");
		}
		board.remove(champion);
		discardPile.add(champion);
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
	public <T> Option<T> pickAnOption(List<Option<T>> options) {
		return null;
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
}
