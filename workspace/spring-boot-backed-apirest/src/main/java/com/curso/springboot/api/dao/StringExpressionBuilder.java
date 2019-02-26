package com.curso.springboot.api.dao;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class StringExpressionBuilder implements ExpressionBuilder<Expression<String>>{
	@Override
	public Expression<String> build(FilterBy filter) throws Exception {
		 return Expressions.asString(filter.getAttrValue());
	}
}
