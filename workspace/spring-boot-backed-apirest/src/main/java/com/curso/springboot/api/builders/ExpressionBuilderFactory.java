package com.curso.springboot.api.builders;

import java.lang.reflect.Method;
import java.util.function.Function;

import org.apache.commons.text.CaseUtils;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class ExpressionBuilderFactory {

	
	/**
	 * 
	 * @param filter
	 * @param operation la operación lógica que se ejecutará como parte de una claúsula where.
	 * @return
	 * @throws Exception
	 */
	public static final ExpressionBuilder<?> getExpressionBuilder(FilterBy filter, Method operation) throws Exception {
		// Decidimos en función de la operation: eq, ne, in, between...
		Class<?> params[] = operation.getParameterTypes();
		// Verificamos que todos los tipos sean un Expression.
		for (Class<?> clazz : params) {
			if (!(clazz.isAssignableFrom(Expression.class) || clazz.isAssignableFrom(Expression[].class))) {
				throw new Exception("Tipo de parámetro no es un Expression<?>");
			}
		}
		switch(params.length) {
		// En el caso de que el método necesite un único parámetro hay que comprobar si es un array.
		case 1:
			if (params[0].isAssignableFrom(Expression[].class)) {
				return asArrayExpression(filter);
			} else {
				return asExpression(filter);
			}
		case 2:
			return asBetweenExpression(filter);
		default:
			//TODO: Hay que implementar un tipo de excepción específico.
			throw new Exception("Número de parámetros erróneo.");
		}
		
	}
	/**
	 * Se devuelve el ExpressionBuilder que construye una única expresión, se corresponde con las operadores lógicos unitarios: eq, ne, goe, gt, loe, lt, etc
	 * @param filter
	 * @return
	 * @throws Exception
	 */

	private static ExpressionBuilder<Expression<?>> asExpression(FilterBy filter) throws Exception {
		Function<FilterBy, Expression<?>> fn = (filterBy) -> {
			try {

				String methodName = "as" + CaseUtils.toCamelCase(filter.getAttrType(), true, null);
				
				Method typeMethod = TypesFactory.class.getMethod(methodName);
				Method expressionMethod = Expressions.class.getMethod(methodName, (Class<?>)typeMethod.invoke(null, new Object[] {}));
				Method valueMethod = ExpressionValuesFactory.class.getMethod(methodName, String.class, String.class);

				return (Expression<?>) expressionMethod.invoke(null,
						valueMethod.invoke(null, filterBy.getAttrValue().trim(), filterBy.getAttrFormat()));

			} catch (Exception e) {
				// TODO: hay que crear una excepción personalizada y relanzarla.
				return null;
			}
		};

		ExpressionBuilder<Expression<?>> expressionBuilder = new ExpressionBuilder<Expression<?>>() {

			@Override
			protected Expression<?> operation(FilterBy filter) {
				return fn.apply(filter);
			}
		};

		return expressionBuilder;
	}
	
	private static ExpressionBuilder<Expression<?>[]> asArrayExpression(FilterBy filter) throws Exception {
		Function<FilterBy, Expression<?>[]> fn = (filterBy) -> {
			try {

				String methodName = "as" + CaseUtils.toCamelCase(filter.getAttrType(), true, null);
				Method typeMethod = TypesFactory.class.getMethod(methodName);
				Method expressionMethod = Expressions.class.getMethod(methodName, (Class<?>)typeMethod.invoke(null, new Object[] {}));
				Method valueMethod = ExpressionValuesFactory.class.getMethod(methodName, String.class, String.class);

				String[] values = filter.getAttrValue().split(",");
				Expression<?>[] expressions = new Expression<?>[values.length];
				for (int i = 0; i<values.length; i++ ) {
					expressions[i] = (Expression<?>) expressionMethod.invoke(null,
							valueMethod.invoke(null, values[i].trim(), filterBy.getAttrFormat()));
				}
				return expressions;

			} catch (Exception e) {
				// TODO: hay que crear una excepción personalizada y relanzarla.
				return null;
			}
		};

		ExpressionBuilder<Expression<?>[]> expressionBuilder = new ExpressionBuilder<Expression<?>[]>() {

			@Override
			protected Expression<?>[] operation(FilterBy filter) {
				return fn.apply(filter);
			}
		};

		return expressionBuilder;
	}
	
	private static ExpressionBuilder<Expression<?>[]> asBetweenExpression(FilterBy filter) throws Exception {
		Function<FilterBy, Expression<?>[]> fn = (filterBy) -> {
			try {

				String methodName = "as" + CaseUtils.toCamelCase(filter.getAttrType(), true, null);
				Method typeMethod = TypesFactory.class.getMethod(methodName);
				Method expressionMethod = Expressions.class.getMethod(methodName, (Class<?>)typeMethod.invoke(null, new Object[] {}));
				Method valueMethod = ExpressionValuesFactory.class.getMethod(methodName, String.class, String.class);
				
				String[] values = filter.getAttrValue().split(",");
				if (values.length != 2) {
					throw new Exception("Wrong number of values : " + values.length);
				}
				Expression<?>[] expressions = new Expression<?>[2];
				expressions[0] =  (Expression<?>) expressionMethod.invoke(null,
						valueMethod.invoke(null, values[0].trim(), filterBy.getAttrFormat()));
				expressions[1] =  (Expression<?>) expressionMethod.invoke(null,
						valueMethod.invoke(null, values[1].trim(), filterBy.getAttrFormat()));

				return expressions;
				
			} catch (Exception e) {
				// TODO: hay que crear una excepción personalizada y relanzarla.
				return null;
			}
		};

		ExpressionBuilder<Expression<?>[]> expressionBuilder = new ExpressionBuilder<Expression<?>[]>() {

			@Override
			protected Expression<?>[] operation(FilterBy filter) {
				return fn.apply(filter);
			}
		};

		return expressionBuilder;
	}
}
