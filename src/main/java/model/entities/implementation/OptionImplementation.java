package model.entities.implementation;

import model.entities.Option;

public class OptionImplementation<T> implements Option<T> {

	private Object entity;

	public OptionImplementation(Object entity) {
		this.entity = entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getStoredEntity() {
		return (T) entity;
	}

	@Override
	public String getOptionString() {
		return entity.toString();
	}

}
