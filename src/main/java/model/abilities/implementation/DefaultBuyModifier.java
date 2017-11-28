package model.abilities.implementation;

import model.abilities.BuyModifier;
import model.cards.Card;
import model.entities.Player;

public class DefaultBuyModifier implements BuyModifier {

	private Player player;

	public DefaultBuyModifier(Player player) {
		this.player = player;
	}

	@Override
	public void apply(Card card) {
		player.getDiscardPile().add(card);
	}

}
