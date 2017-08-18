package model.entities;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Champion;

/**
 * 
 * Interface which defines a player, listing all the possible actions that a
 * player can take
 * 
 * @author Ivan
 *
 */
public interface Player {

	public void draw();

	public void discard(Card card);

	public void play(Card card);

	public List<Card> getHand();

	public Deck getDeck();

	public List<Card> getDiscardPile();

	public void startTurn();

	public void endTurn();

	public void increaseGold(int number);

	public void increaseHealth(int number);

	public void increaseDamage(int number);

	public List<Champion> getBoard();

	public List<Card> getActions();

	public void buy(Card card);

	public int getHealth();

	public int getDamage();

	public int getGold();

	public String getName();

	public <T> Option<T> pickAnOption(List<Option<T>> options);
}
