package model.enums;

import model.abilities.BuyModifier;
import model.abilities.implementation.DefaultBuyModifier;
import model.cards.Card;
import model.cards.implementation.Action;
import model.entities.Player;

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
			player.setQuery("TARGET DISCARD");
		}
	},
	SELFDISCARD {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET SELFDISCARD");
		}
	},
	SACRIFICE {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET SACRIFICE");
		}
	},
	STUN {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET STUN");
		}
	},
	PREPARE {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET PREPARE");
		}
	},
	CHAMPION_TO_TOP {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET CHAMPION_TO_TOP");
		}
	},
	CARD_TO_TOP {
		@Override
		public void activate(Player player) {
			player.setQuery("TARGET CARD_TO_TOP");
		}
	},
	BUY_ACTION_TO_TOP {
		@Override
		public void activate(Player player) {
			player.setBuyModifier(new BuyModifier() {
				@Override
				public void apply(Player player, Card card) {
					if (card instanceof Action) {
						player.getDeck().putCardOnTop(card);
						player.setBuyModifier(new DefaultBuyModifier());
					} else {
						player.getDiscardPile().add(card);
					}
				}
			});
		}
	},
	BUY_CARD_TO_HAND {
		@Override
		public void activate(Player player) {
			player.setBuyModifier(new BuyModifier() {
				@Override
				public void apply(Player player, Card card) {
					player.getHand().add(card);
					player.setBuyModifier(new DefaultBuyModifier());
				}
			});
		}
	},
	BUY_CARD_TO_TOP {
		@Override
		public void activate(Player player) {
			player.setBuyModifier(new BuyModifier() {
				@Override
				public void apply(Player player, Card card) {
					player.getDeck().putCardOnTop(card);
					player.setBuyModifier(new DefaultBuyModifier());
				}
			});
		}
	};

	public abstract void activate(Player player);
}
