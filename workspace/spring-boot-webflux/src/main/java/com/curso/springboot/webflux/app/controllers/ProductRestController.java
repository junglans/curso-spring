package com.curso.springboot.webflux.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.webflux.app.model.documents.Product;
import com.curso.springboot.webflux.app.services.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

	@Autowired
	private ProductService service;
	
	@RequestMapping(value= "/list", method=RequestMethod.GET)
	public Flux<Product> listAll() throws Exception {
		
		Flux<Product> products = service.listAll();
		return products;
		 
	}
	
	@RequestMapping(value= "/{id}", method=RequestMethod.GET)
	public Mono<Product> findById(@PathVariable(name="id") String id) throws Exception {
		
		Mono<Product> product = service.findById(id).doOnNext(item -> System.out.println(item.toString()));
		return product;
		 
		 
	}
}
