package com.curso.springboot.jpa.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.curso.springboot.jpa.models.entity.UserEntity;

public interface IUserDAO extends CrudRepository<UserEntity, Long>{

	UserEntity findByUsername(String username);
}
