package com.curso.springboot.webflux.app.services;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.springboot.webflux.app.model.dao.IProductDAO;
import com.curso.springboot.webflux.app.model.documents.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private IProductDAO dao;
	
	public Flux<Product> listAll()  throws Exception {
		
		Flux<Product> products = dao.findAll()
				.map(item -> {
						item.setName(item.getName().toUpperCase());
						return item;
					});
		
		return products;
	}
	
	public Mono<Product> findById(String id) throws Exception {
		return dao.findById(id);
	}
}
