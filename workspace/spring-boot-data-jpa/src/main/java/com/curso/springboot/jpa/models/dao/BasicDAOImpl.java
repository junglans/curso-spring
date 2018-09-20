package com.curso.springboot.jpa.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.jpa.models.entity.Identifiable;

@Repository
public class BasicDAOImpl<T extends Identifiable<?>> implements IBasicDAO<T> {

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Almacena la clase del genérico T
	 */
	protected Class<T> typeOf;

	public BasicDAOImpl() {
	}

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
		if (entity.getId() != null) {
			em.merge(entity);
		} else {
			em.persist(entity);
		}
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public T findOne(Long id) {
		 return em.find(typeOf, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setTypeOf(Class<?> typeOf) {
		this.typeOf = (Class<T>)typeOf;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		T entity = findOne(id);
		if (entity != null) {
			em.remove(entity);
		}
		
	}

}
