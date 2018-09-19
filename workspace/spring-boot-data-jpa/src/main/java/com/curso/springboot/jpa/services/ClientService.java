package com.curso.springboot.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.jpa.models.dao.IBasicDAO;
import com.curso.springboot.jpa.models.dto.ClientDTO;
import com.curso.springboot.jpa.models.entity.ClientEntity;
import com.curso.springboot.jpa.utils.MapperUtil;

@Service
public class ClientService {

	 @Autowired
	 private MapperUtil mapper;
	 @Autowired
	 @Qualifier("clientDao")
	 private IBasicDAO<ClientEntity> clientDao;
	 
	 @Transactional(propagation = Propagation.REQUIRED)
	 public List<ClientDTO> findAll() {
		 
		 return mapper.map(clientDao.findAll(),  ClientDTO.class);
		 
	 }
}
