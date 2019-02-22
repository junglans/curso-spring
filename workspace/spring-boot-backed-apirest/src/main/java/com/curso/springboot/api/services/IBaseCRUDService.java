package com.curso.springboot.api.services;

import java.util.List;

 

public interface IBaseCRUDService<E> {
	
	List<E> findAll() throws Exception;

	E save(E entity) throws Exception;

	void delete(E entity) throws Exception;

	E findById(Long id) throws Exception;
}
