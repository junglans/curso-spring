package com.curso.springboot.api.builders;

import com.curso.springboot.api.dao.FilterBy;

public interface Builder<E> {
	E build(FilterBy filter) throws Exception;
}
