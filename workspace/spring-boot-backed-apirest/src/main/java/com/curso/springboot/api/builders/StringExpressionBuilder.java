package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class StringExpressionBuilder implements ExpressionBuilder<String> {
	@Override
	public Expression<String> build(FilterBy filter) throws Exception {
		 return Expressions.asString(filter.getAttrValue());
	}
}
