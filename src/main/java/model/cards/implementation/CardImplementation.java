package model.cards.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.abilities.Ability;
import model.cards.Card;
import model.entities.Player;
import model.enums.AbilityTrigger;
import model.enums.Faction;
import model.enums.HeroClass;

public abstract class CardImplementation implements Card {

	private static int currentId = 0;

	private List<Ability> abilities;

	private Faction faction;

	private int cost;

	private String name;

	private int id;

	private String code;

	private String description;

	private HeroClass heroClass;

	private static final Map<Integer, Card> CARD_MAP = new HashMap<>();

	public CardImplementation(List<Ability> abilities, Faction faction, int cost, String name, String code,
			String description, HeroClass heroClass) {
		this.abilities = abilities;
		this.faction = faction;
		this.cost = cost;
		this.name = name;
		this.code = code;
		this.description = description;
		this.heroClass = heroClass;
		this.id = getUniqueCardId();
		CARD_MAP.put(this.id, this);
	}

	public CardImplementation(Card card) {
		this.abilities = card.getAbilities();
		this.faction = card.getFaction();
		this.cost = card.getCost();
		this.name = card.getName();
		this.code = card.getCode();
		this.description = card.getDescription();
		this.heroClass = card.getHeroClass();
		this.id = getUniqueCardId();
		CARD_MAP.put(this.id, this);
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
	public void activate(Player player, AbilityTrigger trigger) {
		for (Ability ability : abilities) {
			if (ability.getTrigger().equals(trigger)) {
				ability.activate(player);
			}
		}
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public int getId() {
		return id;
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
	public HeroClass getHeroClass() {
		return heroClass;
	}

	private static int getUniqueCardId() {
		return currentId++;
	}

	public static Card getCardById(int id) {
		return CARD_MAP.get(id);
	}
}
