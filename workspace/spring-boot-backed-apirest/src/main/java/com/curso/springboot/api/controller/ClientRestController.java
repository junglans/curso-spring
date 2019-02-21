package com.curso.springboot.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.api.entity.ClientEntity;
import com.curso.springboot.api.services.IClientService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value="/api/client")
public class ClientRestController {

	@Autowired
	private IClientService service;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<ClientEntity> findAll() throws Exception {
		return service.findAll();
	}
}
