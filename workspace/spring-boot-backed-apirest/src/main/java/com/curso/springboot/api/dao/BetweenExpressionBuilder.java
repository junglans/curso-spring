package com.curso.springboot.api.dao;

import com.querydsl.core.types.Expression;

public interface BetweenExpressionBuilder<E> extends Builder<Expression<E>[]> {
	Expression<E>[] build(FilterBy filter) throws Exception;
}
