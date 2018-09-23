package model.entities;

import model.enums.TargetSubtype;

public interface Targetable {

	public final static String ACTIVATE = "ACTIVATE";

	public final static String CURRENT_HEALTH = "CURRENT_HEALTH";

	public void addSubtype(TargetSubtype subtype);

	public void removeSubtype(TargetSubtype subtype);

	public boolean isSubtype(TargetSubtype subtype);

	public void takeMessage(String message);

	public void alert(TargetSubtype subtype);

	public void put(String name, Integer value);

	public void remove(String name);

	public Integer get(String name);

	public int getId();

	public Targetable copy();
}
