package model.abilities.implementation;

import model.abilities.BuyModifier;
import model.cards.Card;
import model.entities.Player;

public class DefaultBuyModifier implements BuyModifier {

	@Override
	public void apply(Player player, Card card) {
		player.getDiscardPile().add(card);
	}

}
