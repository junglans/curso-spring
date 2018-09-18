package com.curso.springboot.app.models.dao;

import java.util.List;

public interface IBasicDAO<T> {

	List<T> findAll();
}
