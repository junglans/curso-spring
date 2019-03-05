package com.curso.springboot.api.services;

import java.util.List;
import com.curso.springboot.api.dao.FilterBy;

public interface IBaseCRUDService<E> {
	List<E> findAll(FilterBy[] filter) throws Exception;
	
	List<E> findAll() throws Exception;

	E save(E entity) throws Exception;

	void delete(E entity) throws Exception;

	E findById(Long id) throws Exception;
}
