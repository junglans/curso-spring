package com.curso.springboot.jpa.models.dao;

import java.util.List;

import com.curso.springboot.jpa.models.entity.Identifiable;

public interface IBasicDAO<T extends Identifiable<?>> {

	List<T> findAll();
	T save(T entity);
	void delete(Long id);
	T findOne(Long id);
	void setTypeOf(Class<?> typeOf);
	
}
