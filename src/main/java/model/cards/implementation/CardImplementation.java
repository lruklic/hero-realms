package model.cards.implementation;

import java.util.List;
import java.util.stream.Collectors;

import model.abilities.Ability;
import model.cards.Card;
import model.entities.Player;
import model.entities.implementation.TargetableImplementation;
import model.enums.AbilityTrigger;
import model.enums.Faction;
import model.enums.TargetSubtype;

public abstract class CardImplementation extends TargetableImplementation implements Card {

	private List<Ability> abilities;

	private Faction faction;

	private int cost;

	private String name;

	private String code;

	private String description;

	protected CardImplementation(CardImplementation card) {
		super(card);
		List<Ability> abilities = this.abilities.stream().map(Ability::copy).collect(Collectors.toList());
		this.abilities = abilities;
		this.code = card.code;
		this.cost = card.cost;
		this.description = card.description;
		this.faction = card.faction;
		this.name = card.name;
	}

	public CardImplementation(List<Ability> abilities, Faction faction, int cost, String name, String code,
			String description) {
		this.abilities = abilities;
		this.faction = faction;
		this.cost = cost;
		this.name = name;
		this.code = code;
		this.description = description;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public Faction getFaction() {
		return faction;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void goIntoPlay(Player player) {
		checkBeforePlayRestrictions(player);
		activateOnPlayAbilities(player);
	}

	private void activateOnPlayAbilities(Player player) {
		triggerAbilities(player, AbilityTrigger.BEFORE_PLAY);
	}

	private void checkBeforePlayRestrictions(Player player) {
		triggerAbilities(player, AbilityTrigger.BEFORE_PLAY);
		if (isSubtype(TargetSubtype.UNFULFILLED)) {
			removeSubtype(TargetSubtype.UNFULFILLED);
			throw new IllegalArgumentException("Cannot be played, restrictions are not fulfilled!");
		}
	}

	private void triggerAbilities(Player player, AbilityTrigger trigger) {
		abilities.forEach(ability -> ability.trigger(player, trigger));
	}
}
