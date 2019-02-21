package com.curso.springboot.api.services;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.api.dao.IClientDAO;
import com.curso.springboot.api.entity.ClientEntity;
@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDAO dao;
	
	@Override
	@Transactional(readOnly= true)
	public List<ClientEntity> findAll() throws Exception {
		 
		return (List<ClientEntity>) dao.findAll();
	}

	
}
