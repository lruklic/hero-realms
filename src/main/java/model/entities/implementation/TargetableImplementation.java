package model.entities.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import model.entities.Targetable;
import model.enums.TargetSubtype;

public abstract class TargetableImplementation implements Targetable {

	private HashMap<String, Integer> numericProperties;

	private HashSet<TargetSubtype> subtypes;

	private int id;

	private static final Map<Integer, Targetable> TARGETABLES = new HashMap<>();

	protected TargetableImplementation(TargetableImplementation targetable) {
		this.numericProperties = new HashMap<>(targetable.numericProperties);
		this.subtypes = new HashSet<>(targetable.subtypes);
	}

	public TargetableImplementation() {
		numericProperties = new HashMap<>();
		subtypes = new HashSet<>();
		id = getNewId();
		TARGETABLES.put(id, this);
	}

	@Override
	public void addSubtype(TargetSubtype subtype) {
		subtypes.add(subtype);
	}

	private int getNewId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeSubtype(TargetSubtype subtype) {
		subtypes.remove(subtype);
	}

	@Override
	public boolean isSubtype(TargetSubtype subtype) {
		return subtypes.contains(subtype);
	}

	@Override
	public void put(String name, Integer value) {
		numericProperties.put(name, value);
	}

	@Override
	public void remove(String name) {
		numericProperties.remove(name);
	}

	@Override
	public Integer get(String name) {
		return numericProperties.get(name);
	}

	@Override
	public int getId() {
		return id;
	}

	public static Targetable getById(int id) {
		return TARGETABLES.get(id);
	}
}
