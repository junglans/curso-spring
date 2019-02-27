package com.curso.springboot.api.dao;


public interface Builder<E> {
	E build(FilterBy filter) throws Exception;
}
