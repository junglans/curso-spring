package com.curso.springboot.jpa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.models.bean.InvoiceBean;
import com.curso.springboot.jpa.services.ClientService;
import com.curso.springboot.jpa.utils.MapperUtil;

@Controller
@RequestMapping("invoices")
@SessionAttributes("invoice") 
public class InvoiceController {
	@Autowired
	private ClientService clientService;
	@Autowired
	private MapperUtil mapper;
	
	@RequestMapping(value = "/form/{clientId}", method = RequestMethod.GET)
	public String newInvoice(@PathVariable(value = "clientId") Long clientId, Model model, RedirectAttributes flash) {
		
		ClientBean client = mapper.map(clientService.findById(clientId), ClientBean.class);
		if (client == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:clients";
		}
		
		InvoiceBean invoice = new InvoiceBean();
		invoice.setClient(client);
		
		model.addAttribute("title", "Crear Factura");
		model.addAttribute("invoice", invoice);
		
		return "invoices/form";
	}

}
