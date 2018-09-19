package com.curso.springboot.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.services.ClientService;
import com.curso.springboot.jpa.utils.MapperUtil;

@Controller
public class ClientController {

	@Autowired
	private ClientService clientService;
	@Autowired
	private MapperUtil mapper;
	
	@RequestMapping(value="/clients", method=RequestMethod.GET )
	public String list(Model model) {
		model.addAttribute("title", "Listado de Clientes");
		model.addAttribute("clientList", mapper.map(clientService.findAll(), ClientBean.class));
		return "clients";
	}
}
