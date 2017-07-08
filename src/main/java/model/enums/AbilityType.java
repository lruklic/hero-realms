package model.enums;

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
			//TODO
		}
	},
	SACRIFICE {
		@Override
		public void activate(Player player) {
			//TODO
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
			// TODO
		}
	};
	
	public abstract void activate(Player player);
}
