package com.curso.springboot.jpa.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.curso.springboot.jpa.models.bean.InvoiceBean;
import com.curso.springboot.jpa.models.bean.InvoiceItemBean;
import com.curso.springboot.jpa.models.bean.ProductBean;
import com.curso.springboot.jpa.models.dto.InvoiceDTO;
import com.curso.springboot.jpa.services.IClientService;
import com.curso.springboot.jpa.services.IInvoiceService;
import com.curso.springboot.jpa.services.IProductService;
import com.curso.springboot.jpa.utils.MapperUtil;

@Controller
@RequestMapping("invoices")
@SessionAttributes("invoice")
public class InvoiceController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class);
	@Autowired
	private IClientService clientService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IInvoiceService invoiceService;
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
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String saveInvoice(InvoiceBean invoice, 
							  @RequestParam(name="item_id[]", required=false) Long[] itemId,
							  @RequestParam(name="quantity[]", required=false) Integer[] quantity,
							  RedirectAttributes flash,
							  SessionStatus status) {
		
		for(int ind = 0; ind < itemId.length; ind++) {
			ProductBean product = mapper.map(productService.findById(itemId[ind]), ProductBean.class);
			
			InvoiceItemBean invoiceItem = new InvoiceItemBean();
			invoiceItem.setQuantity(quantity[ind]);
			invoiceItem.setProduct(product);
			
			invoice.addItem(invoiceItem);
			
			LOGGER.info("ID: " + itemId[ind] + ", cantidad :" + quantity[ind]);
		}
		 
		invoiceService.saveInvoice(mapper.map(invoice, InvoiceDTO.class));
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito");
		return "redirect:/invoices/form/" + invoice.getClient().getId();
	}
}
