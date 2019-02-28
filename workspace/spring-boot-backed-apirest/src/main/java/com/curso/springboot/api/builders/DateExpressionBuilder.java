package com.curso.springboot.api.builders;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

public class DateExpressionBuilder implements ExpressionBuilder<Date>{
	@Override
	public Expression<Date> build(FilterBy filter) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(filter.getAttrFormat());
		return Expressions.asDate(sdf.parse(filter.getAttrValue()));
	}
}
