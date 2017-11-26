package model.abilities;

import model.cards.Card;
import model.entities.Player;

public interface BuyModifier {

	public void apply(Player player, Card card);

}
