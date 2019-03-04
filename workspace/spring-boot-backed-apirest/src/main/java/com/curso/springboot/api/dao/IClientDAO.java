package com.curso.springboot.api.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.curso.springboot.api.builders.ExpressionBuilder;
import com.curso.springboot.api.builders.ExpressionBuilderFactory;
import com.curso.springboot.api.entity.ClientEntity;
import com.curso.springboot.api.entity.QClientEntity;
import com.curso.springboot.api.utils.CollectionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public interface IClientDAO extends JpaRepository<ClientEntity, Long>, QuerydslPredicateExecutor<ClientEntity> {

	default List<ClientEntity> findAll(FilterBy... filters) throws Exception {

		QClientEntity qclient = QClientEntity.clientEntity;

		BooleanExpression finalPredicate = null;
		for (FilterBy filter : filters) {

			// Utilizando reflexión recuperamos el campo de búsqueda a partir del nombre del campo que viene en el filtro.
			Field filterField = qclient.getClass().getField(filter.getAttrName());
			Object fieldPath = ((Object) filterField.get(qclient));

			// Recuperamos el método que implementa la operación indicada en el filtro. Nos interesan los métodos que tienen
			// parámetros de tipo Expression<?>.
			Method method = null;
			for (Method m : fieldPath.getClass().getMethods()) {
//				System.out.println(m.getName());
				if (m.getName().equals(filter.getAttrOperation())) {
					switch (m.getParameterTypes().length) {
					case 1: // eq, ne, ge, gt, in, not in
						if ((m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression.class) || 
							 m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression[].class))) {
							method = m;
						}
						break;
					case 2: // between
						if ((m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression.class) && 
							 m.getParameterTypes()[1].equals(com.querydsl.core.types.Expression.class))) {
							method = m;
						}
						break;
					default:
						throw new Exception("Wrong number of parameters :" + m.getParameterTypes().length);
					}
				}
				if (method != null) {
					break;
				}
			}

			ExpressionBuilder<?> builder = ExpressionBuilderFactory.getExpressionBuilder(filter, method);
			// Aqui el resultado de la llmada es un objeto de tipo Expression o Expression[]
			Object val = builder.build(filter);
			BooleanExpression predicate = null;
			switch (method.getParameterTypes().length) {
			case 1:
				predicate = (BooleanExpression) method.invoke(fieldPath, val);
				finalPredicate = (finalPredicate == null) ? predicate : finalPredicate.and(predicate);
				break;
			case 2:
				Object[] values = (Object[]) val;
				predicate = (BooleanExpression) method.invoke(fieldPath, values[0], values[1]);
				finalPredicate = (finalPredicate == null) ? predicate : finalPredicate.and(predicate);
				break;
			}
		}
		return CollectionUtils.iterableToCollection(findAll(finalPredicate));
	}
}
