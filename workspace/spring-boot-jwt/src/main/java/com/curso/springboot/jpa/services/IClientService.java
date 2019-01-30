package com.curso.springboot.jpa.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.curso.springboot.jpa.models.dto.ClientDTO;

public interface IClientService {

	 List<ClientDTO> findAll();
	 Page<ClientDTO> findAll(Pageable pageable);
	 ClientDTO save(ClientDTO client);
	 ClientDTO findById(Long id);
	 void delete(Long id);
	 ClientDTO fetchByIdWithInvoices(Long id);
}
