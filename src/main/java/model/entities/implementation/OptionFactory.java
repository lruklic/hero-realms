package model.entities.implementation;

import java.util.ArrayList;
import java.util.List;

import model.entities.Option;

public class OptionFactory {

	private OptionFactory() {

	}

	public static <T> Option<T> getOption(T entity) {
		return new OptionImplementation<T>(entity);
	}

	public static <T> List<Option<T>> getOptions(List<T> entities) {
		List<Option<T>> options = new ArrayList<>();
		for (T entity : entities) {
			options.add(new OptionImplementation<T>(entity));
		}
		return options;
	}
}
