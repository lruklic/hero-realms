package model.enums;

/**
 * Unique player class that defines starting deck and hero power.
 * 
 * @author lruklic
 *
 */

public enum HeroClass {

	CLERIC(55),
	
	FIGHTER(60),
	
	RANGER(58),
	
	THIEF(52),
	
	WIZARD(50),
	
	NONE(50);
	
	private int health;
	
	private HeroClass(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return this.health;
	}
}
