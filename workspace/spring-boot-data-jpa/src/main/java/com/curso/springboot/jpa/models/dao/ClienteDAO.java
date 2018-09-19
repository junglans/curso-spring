package com.curso.springboot.jpa.models.dao;

import org.springframework.stereotype.Repository;

import com.curso.springboot.jpa.models.entity.ClientEntity;
@Repository("clientDao")
public class ClienteDAO extends BasicDAOImpl<ClientEntity> {

}
