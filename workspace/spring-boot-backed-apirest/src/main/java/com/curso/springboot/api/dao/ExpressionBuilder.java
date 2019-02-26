package com.curso.springboot.api.dao;

public interface ExpressionBuilder<E> {
	E build(FilterBy filter) throws Exception;
}
