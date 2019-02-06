package com.curso.springboot.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.services.IClientService;
import com.curso.springboot.jpa.utils.MapperUtil;
import com.curso.springboot.jpa.view.xml.ClientList;

@RestController
@RequestMapping("api/clients")
public class ClientRestController {
	
	@Autowired
	private MapperUtil mapper;
	
	@Autowired
	private IClientService clientService;
	
	@RequestMapping(value = {"/list"}, method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ClientList list() {
		return new ClientList(mapper.map(clientService.findAll(), ClientBean.class));
	}
}
