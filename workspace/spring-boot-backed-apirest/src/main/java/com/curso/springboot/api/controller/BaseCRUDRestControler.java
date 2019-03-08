package com.curso.springboot.api.controller;

import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.curso.springboot.api.dao.FilterBy;
import com.curso.springboot.api.dao.QueryRequest;
import com.curso.springboot.api.services.IBaseCRUDService;

public abstract class BaseCRUDRestControler<E> {

	protected IBaseCRUDService<E> service;

	public BaseCRUDRestControler(IBaseCRUDService<E> service) {
		this.service = service;
	}

	public ResponseEntity<?> findAll() throws Exception {
		return new ResponseEntity<List<E>>(service.findAll(), HttpStatus.OK);
	}
	
	public ResponseEntity<?> findAll(FilterBy[] filters) throws Exception {
		return new ResponseEntity<List<E>>(service.findAll(filters), HttpStatus.OK);
	}
	
	public ResponseEntity<?> findAll(QueryRequest request) throws Exception {
		return new ResponseEntity<List<E>>(service.findAll(request.getFilters(), request.getSorts()), HttpStatus.OK);
	}

	public ResponseEntity<?> get(Long id) throws Exception {
		return new ResponseEntity<E>(service.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<?> create(E entity) throws Exception {
		return new ResponseEntity<E>(service.save(entity), HttpStatus.OK);
	}

	public ResponseEntity<?> delete(Long id) throws Exception {

		E entity = this.service.findById(id);
		this.service.delete(entity);
		return new ResponseEntity<E>(entity, HttpStatus.OK);
		
	}

	public ResponseEntity<?> update(E entity, Long id, BiConsumer<E, E> fn) throws Exception {

		E current = service.findById(id);
		fn.accept(current, entity);
		E updated = service.save(current);

		return new ResponseEntity<E>(updated, HttpStatus.OK);

	}

}
