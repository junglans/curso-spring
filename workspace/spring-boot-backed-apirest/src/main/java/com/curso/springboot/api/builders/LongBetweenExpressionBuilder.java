package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class LongBetweenExpressionBuilder implements BetweenExpressionBuilder<Long>{

	@SuppressWarnings("unchecked")
	@Override
	public Expression<Long>[] build(FilterBy filter) throws Exception {
		String[] values = filter.getAttrValue().split(",");
		if (values.length != 2) {
			throw new Exception("Wrong number of values : " + values.length);
		}
		Expression<?>[] expressions = new Expression<?>[2];
		expressions[0] = Expressions.asNumber(Long.parseLong(values[0].trim()));
		expressions[1] = Expressions.asNumber(Long.parseLong(values[1].trim()));
		return (Expression<Long>[])expressions;
	}
 
}
