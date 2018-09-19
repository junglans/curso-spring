package com.curso.springboot.jpa.models.dao;

import java.util.List;

public interface IBasicDAO<T> {

	List<T> findAll();
	T save(T entity);
	void setTypeOf(Class<?> typeOf);
	
}
