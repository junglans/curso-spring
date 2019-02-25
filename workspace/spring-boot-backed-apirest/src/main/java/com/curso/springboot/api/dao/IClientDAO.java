package com.curso.springboot.api.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.curso.springboot.api.entity.ClientEntity;
import com.curso.springboot.api.entity.QClientEntity;
import com.curso.springboot.api.utils.CollectionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public interface IClientDAO extends JpaRepository<ClientEntity, Long>, QuerydslPredicateExecutor<ClientEntity> {

	default List<ClientEntity> findAll(FilterBy...filters) throws Exception {
		
		QClientEntity qclient = QClientEntity.clientEntity;

		BooleanExpression finalPredicate = null;
		for (FilterBy filter: filters) {
			
			Field filterField = null;
			for (Field field : qclient.getClass().getDeclaredFields()) {
				if (field.getName().equals(filter.getAttrName())) {
					filterField = field;
				}
			}
			
			Object sp = ((Object)filterField.get(qclient));
					
			Method method = null;
			for (Method m: sp.getClass().getMethods()) {
				if (m.getName().equals(filter.getAttrOperation()) && m.getParameterTypes()[0].equals(Object.class)   ) {
					method = m;
				}
			}
			 		
			BooleanExpression predicate = (BooleanExpression)method.invoke(sp, filter.getAttrValue());
			finalPredicate = (finalPredicate == null)? predicate: finalPredicate.and(predicate);
			
		}
	
		 
		return CollectionUtils.iterableToCollection(findAll(finalPredicate));
		 
		 
	}
}
