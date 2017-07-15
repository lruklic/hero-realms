package model.entities.implementation;

import model.entities.Option;

public class OptionImplementation implements Option {

	private Object entity;
	
	public OptionImplementation(Object entity) {
		this.entity = entity;
	}
	
	@Override
	public Object getStoredEntity() {
		return entity;
	}

	@Override
	public String getOptionString() {
		return entity.toString();
	}

}
