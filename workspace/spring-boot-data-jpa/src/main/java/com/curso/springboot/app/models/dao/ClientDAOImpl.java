package com.curso.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.curso.springboot.app.models.entity.ClientEntity;


@Repository
public class ClientDAOImpl implements IBasicDAO<ClientEntity> {

	@Override
	public List<ClientEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
