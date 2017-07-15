package model.entities.implementation;

import model.abilities.Ability;
import model.entities.Option;

public class OptionFactory {

	private OptionFactory() {
		
	}
	
	public static Option getAbilityOption(Ability ability) {
		return new OptionImplementation(ability);
	}
	
}
