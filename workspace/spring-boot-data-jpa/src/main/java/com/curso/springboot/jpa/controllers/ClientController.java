package com.curso.springboot.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.curso.springboot.jpa.models.dao.ClienteDAO;

@Controller
public class ClientController {

	@Autowired
	private ClienteDAO clientDao;
	
	@RequestMapping(method=RequestMethod.GET, value="/clients")
	public String list(Model model) {
		model.addAttribute("title", "Listado de Clientes");
		model.addAttribute("clientList", clientDao.findAll());
		return "clients";
		
	}
}
