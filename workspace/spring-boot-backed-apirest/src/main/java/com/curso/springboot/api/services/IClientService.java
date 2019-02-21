package com.curso.springboot.api.services;

import java.util.List;

 
import com.curso.springboot.api.entity.ClientEntity;

public interface IClientService {

	 List<ClientEntity> findAll() throws Exception;
 
}
