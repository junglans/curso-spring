package com.curso.springboot.api.dao;

import java.text.SimpleDateFormat;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class DateListExpressionBuilder<Date> implements CollectionExpressionBuilder<Date> {

	@SuppressWarnings("unchecked")
	@Override
	public Expression<Date>[] build(FilterBy filter) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat(filter.getAttrFormat());
		
		String[] values = filter.getAttrValue().split(",");
		Expression<?>[] expressions = new Expression<?>[values.length];
		for (int i = 0; i<values.length; i++ ) {
			expressions[i] = Expressions.asDate(sdf.parse(values[i].trim()));
		}
		
		return (Expression<Date>[])expressions;
	}

}
