package com.curso.springboot.api.utils;

import java.util.List;
import java.util.ArrayList;

public class CollectionUtils {

	public static <T> List<T> iterableToCollection(Iterable<T> iterable) {
		List<T> collection = new ArrayList<T>();
		for (T e : iterable) {
			collection.add(e);
		}
		return collection;
	}

}
