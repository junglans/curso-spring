package com.curso.springboot.api.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.curso.springboot.api.builders.ExpressionBuilder;
import com.curso.springboot.api.builders.ExpressionBuilderFactory;
import com.curso.springboot.api.utils.CollectionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.SimpleExpression;

@NoRepositoryBean
public interface IBaseDAO<T, Q> extends JpaRepository<T, Q>, QuerydslPredicateExecutor<T> {

	default List<T> findAll(EntityPathBase<?> entityPathBase, FilterBy[] filters) throws Exception {

		BooleanExpression finalPredicate = getFilterByPredicate(entityPathBase, filters);
		return CollectionUtils.iterableToCollection(findAll(finalPredicate));
	}

	default List<T> findAll(EntityPathBase<?> entityPathBase, FilterBy[] filters, SortBy[] sorts) throws Exception {
		BooleanExpression finalPredicate = getFilterByPredicate(entityPathBase, filters);
		
		if (sorts != null && sorts.length != 0) {
			OrderSpecifier<?>[] orderByExpressions = new OrderSpecifier<?>[sorts.length];
			for (int ind = 0; ind < sorts.length; ind++) {
				ComparableExpression<?> comparable = 
						(ComparableExpression<?>) getFieldPath(new String[] { sorts[ind].getAttrName() }, entityPathBase);
				if (sorts[ind].getOrder().equals(OrderType.ASCENDING)) {
					orderByExpressions[ind] = comparable.asc();
				} else {
					orderByExpressions[ind] = comparable.desc();
				}

			}
			return CollectionUtils.iterableToCollection(findAll(finalPredicate, orderByExpressions));
		} else {
			return CollectionUtils.iterableToCollection(findAll(finalPredicate));
		}
		
	}

	default BooleanExpression getFilterByPredicate(EntityPathBase<?> entityPathBase, FilterBy[] filters)
			throws Exception {
		BooleanExpression finalPredicate = null;
		for (FilterBy filter : filters) {

			Object fieldPath = getFieldPath(filter.getAttrName(), entityPathBase);
			// Recuperamos el método que implementa la operación indicada en el filtro. Nos
			// interesan los métodos que tienen
			// parámetros de tipo Expression<?>.
			Method method = getMethod(filter, fieldPath);
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
		return finalPredicate;
	}

	default Object getFieldPath(String[] attrName, EntityPathBase<?> entityPathBase) throws Exception {

		// Utilizando reflexión recuperamos el campo de búsqueda a partir del nombre del
		// campo que viene en el filtro.
		// El nombre del campo puede ser una lista de nombres separada por ".".
		// Buscamos el primer nivel
		Field filterField = entityPathBase.getClass().getField(attrName[0]);

		Object fieldPath = ((Object) filterField.get(entityPathBase));
		if (attrName.length > 1) {
			if (fieldPath.getClass().isAssignableFrom(ListPath.class)) {

				SimpleExpression<?> qentity = ((ListPath<?, ?>) fieldPath).any();
				fieldPath = getFieldPath(Arrays.copyOfRange(attrName, 1, attrName.length), (EntityPathBase<?>) qentity);

			} else {
				fieldPath = getFieldPath(Arrays.copyOfRange(attrName, 1, attrName.length),
						(EntityPathBase<?>) fieldPath);
			}
		}
		return fieldPath;
	}

	default Method getMethod(FilterBy filter, Object fieldPath) throws Exception {
		Method method = null;
		for (Method m : fieldPath.getClass().getMethods()) {
//			System.out.println(m.getName());
			if (m.getName().equals(filter.getAttrOperation())) {
				switch (m.getParameterTypes().length) {
				case 1: // eq, ne, ge, gt, in, not in
					if ((m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression.class)
							|| m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression[].class))) {
						method = m;
					}
					break;
				case 2: // between
					if ((m.getParameterTypes()[0].equals(com.querydsl.core.types.Expression.class)
							&& m.getParameterTypes()[1].equals(com.querydsl.core.types.Expression.class))) {
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
		return method;
	}
}
