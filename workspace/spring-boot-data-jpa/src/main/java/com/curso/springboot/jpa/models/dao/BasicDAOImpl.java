package com.curso.springboot.jpa.models.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
public class BasicDAOImpl<T> implements IBasicDAO<T> {

	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> typeOf;

	@SuppressWarnings("unchecked")
	public BasicDAOImpl() {
		 this.typeOf = ((Class<T>) ((ParameterizedType) getClass()
			        .getGenericSuperclass()).getActualTypeArguments()[0]);
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
		em.persist(entity);
		return entity;
	}
}
	
