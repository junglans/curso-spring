package com.curso.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.curso.springboot.webflux.app.model.documents.Product;
import com.curso.springboot.webflux.app.services.ProductService;

import reactor.core.publisher.Flux;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@RequestMapping(value= {"/", "/products"}, method=RequestMethod.GET)
	public String listAll(Model model) throws Exception {
		
		Flux<Product> products = service.listAll();
		model.addAttribute("PRODUCTS", products);
		model.addAttribute("TITLE", "Listado de productos");
		return "listProducts";
	}
	
	@RequestMapping(value= {"/products_dd"}, method=RequestMethod.GET)
	public String listAllDataDriver(Model model) throws Exception {
		
		Flux<Product> products = service.listAll().repeat(5000);
		model.addAttribute("PRODUCTS", new ReactiveDataDriverContextVariable(products,2));
		model.addAttribute("TITLE", "Listado de productos");
		return "listProducts";
	}
	
	@RequestMapping(value= {"/products_full"}, method=RequestMethod.GET)
	public String listAllFull(Model model) throws Exception {
		
		Flux<Product> products = service.listAll().repeat(5000);
		model.addAttribute("PRODUCTS",products);
		model.addAttribute("TITLE", "Listado de productos");
		return "listProducts";
	}
	
	@RequestMapping(value= {"/products_chunked"}, method=RequestMethod.GET)
	public String listAllChunked(Model model) throws Exception {
		
		Flux<Product> products = service.listAll().repeat(5000);
		model.addAttribute("PRODUCTS",products);
		model.addAttribute("TITLE", "Listado de productos (chunked)");
		return "listProductsChunked";
	}
}
