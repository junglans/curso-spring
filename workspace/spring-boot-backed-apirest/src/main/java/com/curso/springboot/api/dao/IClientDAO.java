package com.curso.springboot.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.curso.springboot.api.entity.ClientEntity;
@Repository
public interface IClientDAO extends CrudRepository<ClientEntity, Long> {
}
