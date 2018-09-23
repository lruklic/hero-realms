package model.entities.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.InvalidUserActionException;
import model.abilities.Ability;
import model.cards.Card;
import model.cards.Deck;
import model.cards.implementation.Creature;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.TargetSubtype;

public class PlayerImplementation extends TargetableImplementation implements Player {

	private String name;

	private List<Card> hand;

	private Deck deck;

	private List<Card> graveyard;

	private List<Creature> board;

	private List<Card> market;

	protected PlayerImplementation(PlayerImplementation player) {
		super(player);
		this.name = player.name;
		this.deck = player.deck.copy();
		this.hand = player.hand.stream().map(Card::copy).collect(Collectors.toList());
		this.graveyard = player.graveyard.stream().map(Card::copy).collect(Collectors.toList());
		this.board = player.board.stream().map(Creature::copy).collect(Collectors.toList());
		this.market = player.market.stream().map(Card::copy).collect(Collectors.toList());
	}

	public PlayerImplementation(String name) {
		put(CURRENT_HEALTH, STARTING_HEALTH);
		put(CURRENT_SUPPLY, 0);
		addSubtype(TargetSubtype.HAS_HEALTH);
		this.name = name;
	}

	public PlayerImplementation(String name, Deck deck) {
		this(name);
		this.deck = deck;
	}

	@Override
	public List<Card> getHand() {
		return new ArrayList<>(hand);
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public void alert(TargetSubtype subtype) {
		if (subtype == TargetSubtype.HAS_HEALTH) {
			if (get(Player.CURRENT_HEALTH) <= 0) {
				addSubtype(TargetSubtype.DEAD);
			}
		}
	}

	@Override
	public void takeMessage(String message) {
		switch (message) {
		case PLAY:
			int cardId = get("cardId");
			Card card = hand.stream().filter(c -> c.getId() == cardId).findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Card not found"));
			card.goIntoPlay(this);
			hand.remove(card);
			break;
		case DRAW:
			hand.add(deck.drawCard());
			break;
		case ACTIVATE:
			int abilityId = get("abilityId");
			Ability ability = (Ability) TargetableImplementation.getById(abilityId);
			ability.trigger(this, AbilityTrigger.CLICK);
			break;
		default:
			throw new InvalidUserActionException("Invalid action specified!");
		}
	}

	@Override
	public List<Creature> getBoard() {
		return new ArrayList<>(board);
	}

	@Override
	public Player copy() {
		return new PlayerImplementation(this);
	}

	@Override
	public List<Card> getGraveyard() {
		return new ArrayList<>(graveyard);
	}

	@Override
	public List<Card> getMarket() {
		return market;
	}
}
