package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class StringListExpressionBuilder implements CollectionExpressionBuilder<String>{

	@SuppressWarnings("unchecked")
	@Override
	public Expression<String>[] build(FilterBy filter) throws Exception {
		String[] values = filter.getAttrValue().split(",");
		Expression<?>[] expressions = new Expression<?>[values.length];
		for (int i = 0; i<values.length; i++ ) {
			expressions[i] = Expressions.asString(values[i].trim());
		}
		return (Expression<String>[])expressions;
	}


}
