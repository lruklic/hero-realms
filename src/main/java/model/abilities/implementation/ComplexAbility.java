package model.abilities.implementation;

import model.abilities.Ability;
import model.entities.Targetable;
import model.enums.AbilityTrigger;

public class ComplexAbility extends AbilityImplementation {

	private Ability effect;

	private Ability precondition;

	private Ability postcondition;

	private AbilityTrigger trigger;

	protected ComplexAbility(ComplexAbility ability) {
		super(ability);
		this.effect = ability.effect.copy();
		this.precondition = ability.precondition.copy();
		this.postcondition = ability.postcondition.copy();
		this.trigger = ability.trigger;
	}

	public ComplexAbility(Ability effect, Ability precondition, Ability postcondition, AbilityTrigger trigger) {
		super();
		this.effect = effect;
		this.precondition = precondition;
		this.postcondition = postcondition;
		this.trigger = trigger;
	}

	@Override
	public void trigger(Targetable target, AbilityTrigger abilityTrigger) {
		if (trigger == abilityTrigger) {
			precondition.trigger(target, trigger);
			effect.trigger(target, trigger);
			postcondition.trigger(target, trigger);
		}
	}

	@Override
	public Ability copy() {
		return new ComplexAbility(this);
	}
}
