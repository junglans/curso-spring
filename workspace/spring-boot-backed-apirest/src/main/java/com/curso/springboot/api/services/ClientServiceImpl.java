package com.curso.springboot.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.api.dao.FilterBy;
import com.curso.springboot.api.dao.IClientDAO;
import com.curso.springboot.api.entity.ClientEntity;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDAO clientDao;
	
	@Override
	@Transactional(readOnly= true)
	public List<ClientEntity> findAll() throws Exception {
		return (List<ClientEntity>) clientDao.findAll();
	}
	
	@Override
	@Transactional(readOnly= true)
	public ClientEntity findById(Long id) throws Exception {
		return clientDao.findById(id).get();
	}

	@Override
	@Transactional
	public ClientEntity save(ClientEntity client) throws Exception {
		return clientDao.save(client);
	}

	@Override
	@Transactional
	public void delete(ClientEntity entity) throws Exception {
		clientDao.delete(entity);
	}

	@Override
	@Transactional(readOnly= true)
	public List<ClientEntity> findAll(FilterBy...filter) throws Exception {
		return (List<ClientEntity>) clientDao.findAll(filter);
	}

}
