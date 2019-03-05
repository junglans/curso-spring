package com.curso.springboot.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.curso.springboot.api.entity.ClientEntity;
import com.curso.springboot.api.entity.QClientEntity;
 
@Repository
public interface IClientDAO extends IBaseDAO<ClientEntity, Long> {
	default List<ClientEntity> findAll(FilterBy... filters) throws Exception {
		return findAll(QClientEntity.clientEntity, filters);
	}
}
