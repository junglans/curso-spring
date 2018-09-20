package com.curso.springboot.jpa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.models.dto.ClientDTO;
import com.curso.springboot.jpa.services.ClientService;
import com.curso.springboot.jpa.utils.MapperUtil;

@Controller
@SessionAttributes("client") // Se guarda el objeto cliente en la sesión.
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
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("title", "Formulario de Cliente");
		model.addAttribute("client", new ClientBean());
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String save(@ModelAttribute("client") @Valid ClientBean client, // el model atribute se toma de la sesión.
						BindingResult bindingResult, 
						Model model, SessionStatus status) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("title", "Formulario de Cliente");
			return "form";
		}
		clientService.save( mapper.map(client, ClientDTO.class));
		status.setComplete(); // Se elimina el objeto cliente de la sesión.
		return "redirect:/clients";
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable(value="id") Long id) {
		
		if (id != null) {
			clientService.delete(id);
		}
		
		return "redirect:/clients";
	}
	
	@RequestMapping(value="/form/{id}", method=RequestMethod.GET)
	public String findById(@PathVariable(value="id")Long id, Model model) {
		ClientBean client = null;
		if (id != null) {
			
			client = mapper.map(clientService.findById(id), ClientBean.class);
			if (client != null) {
				model.addAttribute("title", "Formulario de Cliente");
				model.addAttribute("client",client);
				return "form";
			} else {
				return "redirect:/clients";
			}
			
		} else {
			return "redirect:/clients";
		}
		
	}
}
