package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;

public abstract class ExpressionBuilder<E> implements Builder  {
	public E build(FilterBy filter) throws Exception {
		return operation(filter);
	}
	protected abstract E operation(FilterBy filter);
}