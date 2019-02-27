package com.curso.springboot.api.dao;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class LongListExpressionBuilder implements CollectionExpressionBuilder<Long>{

	@SuppressWarnings("unchecked")
	@Override
	public Expression<Long>[] build(FilterBy filter) throws Exception {
		String[] values = filter.getAttrValue().split(",");
		Expression<?>[] expressions = new Expression<?>[values.length];
		for (int i = 0; i<values.length; i++ ) {
			expressions[i] = Expressions.asNumber(Long.parseLong(values[i].trim()));
		}
		return (Expression<Long>[])expressions;
	}
}
