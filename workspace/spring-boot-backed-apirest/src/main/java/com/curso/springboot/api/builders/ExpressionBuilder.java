package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;
import com.querydsl.core.types.Expression;

public interface ExpressionBuilder<E> extends Builder<Expression<E>>{
	Expression<E> build(FilterBy filter) throws Exception;

}
