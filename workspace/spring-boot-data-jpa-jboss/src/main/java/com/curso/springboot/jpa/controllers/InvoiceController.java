package com.curso.springboot.jpa.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.curso.springboot.jpa.models.dto.ClientDTO;
import com.curso.springboot.jpa.models.dto.InvoiceDTO;
import com.curso.springboot.jpa.services.IClientService;
import com.curso.springboot.jpa.services.IInvoiceService;
import com.curso.springboot.jpa.services.IProductService;
import com.curso.springboot.jpa.utils.MapperUtil;
@Secured("ROLE_ADMIN")
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

	@RequestMapping(value="/detail/{invoiceId}", method = RequestMethod.GET )
	public String viewInvoice(@PathVariable(value = "invoiceId") Long invoiceId,
							  Model model,
							  RedirectAttributes flash
							  ) {
		
		InvoiceBean invoice = mapper.map(invoiceService.fetchByIdWithClientWithInvoiceItemWithProduct(invoiceId), InvoiceBean.class);
		if (invoice == null) {
			flash.addFlashAttribute("error", "No se encontró la factura.");
			return "redirect:/clients";
		}
		model.addAttribute("title", "Ver factura " + invoice.getDescription());
		model.addAttribute("invoice", invoice);
		return "invoices/detail";
	}
	 
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
	public String saveInvoice(@Valid @ModelAttribute("invoice") InvoiceBean invoice, 
							  BindingResult result,
							  Model model,
							  @RequestParam(name="item_id[]", required=false) Long[] itemId,
							  @RequestParam(name="quantity[]", required=false) Integer[] quantity,
							  RedirectAttributes flash,
							  SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("title", "Crear Factura");
			return "/invoices/form";
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Crear Factura");
			model.addAttribute("error", "Error: Debe seleccionar al menos un producto.");
			return "invoices/form";
		}
		
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
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String saveInvoice(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		InvoiceBean invoice = mapper.map(invoiceService.findInvoiceById(id), InvoiceBean.class);
		if(invoice != null) {
			ClientBean client = invoice.getClient();
			List<InvoiceBean> invoices = client.getInvoices();
			invoices.remove(invoices.indexOf(invoice));
			 
			clientService.save(mapper.map(client, ClientDTO.class));
			invoiceService.deleteInvoice(id);
			
			flash.addFlashAttribute("success", "Factura eliminada con éxito");
			return "redirect:/detail/" + invoice.getClient().getId();
		} else {
			flash.addFlashAttribute("error", "La factura no exite en la base de datos");
			return "redirect:/clients/";
		}
		
		
		
	}
}
