package com.curso.springboot.api.dao;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class LongExpressionBuilder implements ExpressionBuilder<Long>{
	@Override
	public Expression<Long> build(FilterBy filter) throws Exception {
		return Expressions.asNumber(Long.parseLong(filter.getAttrValue()));
	}
}
