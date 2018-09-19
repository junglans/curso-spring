package com.curso.springboot.jpa.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BasicDAOImpl<T> implements IBasicDAO<T> {

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Almacena la clase del genérico T
	 */
	protected Class<?> typeOf;

	public BasicDAOImpl() {}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<T> findAll() {
		String query = "from " + this.typeOf.getSimpleName();
		return em.createQuery(query).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T save(T entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public void setTypeOf(Class<?> typeOf) {
		this.typeOf = typeOf;
	}

}
