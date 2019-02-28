package com.curso.springboot.api.builders;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.function.Function;

import org.apache.commons.text.CaseUtils;
import org.springframework.core.io.FileSystemResource;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class ExpressionBuilderFactory {

	public static final Properties builderTypes = new Properties();
	static {
		try {
			builderTypes.load(new FileSystemResource("src/main/resources/builder_types.properties").getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static final ExpressionBuilder<?> getExpressionBuilder(FilterBy filter) throws Exception {
		// Decidimos en función de la operation: eq, ne, in, between...
		return asBetweenExpression(filter);
	}

	private static ExpressionBuilder<Expression<?>> asExpression(FilterBy filter) throws Exception {
		Function<FilterBy, Expression<?>> fn = (filterBy) -> {
			try {

				String methodName = "as" + CaseUtils.toCamelCase(filter.getAttrType(), true, null);
				Class<?> clazz = Class.forName(builderTypes.getProperty(filter.getAttrType()));
				Method expression = Expressions.class.getMethod(methodName, clazz);
				Method type = TypesFactory.class.getMethod(methodName, String.class, String.class);

				return (Expression<?>) expression.invoke(null,
						type.invoke(null, filterBy.getAttrValue().trim(), filterBy.getAttrFormat()));

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
				Class<?> clazz = Class.forName(builderTypes.getProperty(filter.getAttrType()));
				Method expression = Expressions.class.getMethod(methodName, clazz);
				Method type = TypesFactory.class.getMethod(methodName, String.class, String.class);

				String[] values = filter.getAttrValue().split(",");
				Expression<?>[] expressions = new Expression<?>[values.length];
				for (int i = 0; i<values.length; i++ ) {
					expressions[i] = (Expression<?>) expression.invoke(null,
							type.invoke(null, values[i].trim(), filterBy.getAttrFormat()));
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
				Class<?> clazz = Class.forName(builderTypes.getProperty(filter.getAttrType()));
				Method expression = Expressions.class.getMethod(methodName, clazz);
				Method type = TypesFactory.class.getMethod(methodName, String.class, String.class);
				
				String[] values = filter.getAttrValue().split(",");
				if (values.length != 2) {
					throw new Exception("Wrong number of values : " + values.length);
				}
				Expression<?>[] expressions = new Expression<?>[2];
				expressions[0] =  (Expression<?>) expression.invoke(null,
						type.invoke(null, values[0].trim(), filterBy.getAttrFormat()));
				expressions[1] =  (Expression<?>) expression.invoke(null,
						type.invoke(null, values[1].trim(), filterBy.getAttrFormat()));

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
