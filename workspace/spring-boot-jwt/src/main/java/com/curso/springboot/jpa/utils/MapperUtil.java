package com.curso.springboot.jpa.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

	@Autowired
	private Mapper mapper;

	public MapperUtil() {
	}

	public <F, T> T map(F source, T destination) {
		if (source == null || destination == null)
			return null;
		mapper.map(source, destination);
		return destination;
	}

	public <F, T> List<T> map(Iterable<F> fromList, final Class<T> toClass) {
		List<T> holder = new ArrayList<T>();
		if (fromList.iterator().hasNext()) {
			for (F from : fromList) {
				holder.add(map(from, toClass));
			}
		}
		return holder;
	}

	public <F, T> T map(F from, final Class<T> toClass) {
		if (from == null)
			return null;
		return mapper.map(from, toClass);
	}
}
