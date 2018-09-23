package model.entities;

import java.util.List;

import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Creature;

/**
 * 
 * Interface which defines a player, listing all the possible actions that a
 * player can take
 * 
 * @author Ivan
 *
 */
public interface Player extends Targetable {

	public final static int STARTING_HEALTH = 30;

	public final static String CURRENT_SUPPLY = "CURRENT_SUPPLY";

	public final static String PLAY = "PLAY";

	public final static String DRAW = "DRAW";

	public List<Card> getHand();

	public Deck getDeck();

	public void startTurn();

	public void endTurn();

	public String getName();

	public List<Creature> getBoard();

	public List<Card> getGraveyard();

	public List<Card> getMarket();

	@Override
	public Player copy();
}
