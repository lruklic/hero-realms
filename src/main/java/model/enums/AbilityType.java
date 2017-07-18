package model.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.cards.Card;
import model.cards.implementation.Champion;
import model.entities.Option;
import model.entities.Player;
import model.entities.implementation.OptionFactory;

public enum AbilityType {

	GOLD {
		@Override
		public void activate(Player player) {
			player.increaseGold(1);
		}
	},
	DAMAGE {
		@Override
		public void activate(Player player) {
			player.increaseDamage(1);
		}
	},
	HEAL {
		@Override
		public void activate(Player player) {
			player.increaseHealth(1);
		}
	},
	DRAW {
		@Override
		public void activate(Player player) {
			player.draw();
		}
	},
	DISCARD {
		@Override
		public void activate(Player player) {
			// TODO
		}
	},
	SELFDISCARD {
		@Override
		public void activate(Player player) {
			Option<Card> option = player.pickAnOption(OptionFactory.getOptions(player.getHand()));
			player.discard(option.getStoredEntity());
		}
	},
	SACRIFICE {
		@Override
		public void activate(Player player) {
			List<Card> cards = new ArrayList<>();
			cards.addAll(player.getDiscardPile());
			cards.addAll(player.getHand());
			Card card = player.pickAnOption(OptionFactory.getOptions(cards)).getStoredEntity();
			player.getDiscardPile().remove(card);
			player.getHand().remove(card);
		}
	},
	STUN {
		@Override
		public void activate(Player player) {
			// TODO
		}
	},
	PREPARE {
		@Override
		public void activate(Player player) {
			List<Option<Champion>> options = OptionFactory
					.getOptions(player.getBoard().stream().filter(c -> c.isTapped()).collect(Collectors.toList()));
			player.pickAnOption(options).getStoredEntity().setTapped(false);
		}
	};

	public abstract void activate(Player player);
}
