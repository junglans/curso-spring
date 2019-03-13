package com.curso.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.curso.springboot.webflux.app.model.dao.IProductDAO;
import com.curso.springboot.webflux.app.model.documents.Product;


import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebFluxApplication implements CommandLineRunner{

	public static Logger logger = LoggerFactory.getLogger(SpringBootWebFluxApplication.class);
	@Autowired
	private IProductDAO dao;
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebFluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("product").subscribe();
		
		Flux.just(new Product("TV Panasonic Pantalla LCD", 456.89),
				new Product("Sony Camara HD Digital", 177.89),
				new Product("Apple iPod", 46.89),
				new Product("Sony Notebook", 846.89),
				new Product("Hewlett Packard Multifuncional", 200.89),
				new Product("Bianchi Bicicleta", 70.89),
				new Product("HP Notebook Omen 17", 2500.89),
				new Product("Mica Cómoda 5 Cajones", 150.89),
				new Product("TV Sony Bravia OLED 4K Ultra HD", 2255.89),
				new Product("Alcatel A3 10\"", 120.00))
			   .map(item -> { 
				   item.setCreatedAt(new Date());
				   return item;
				   })
		       .flatMap(item -> dao.save(item))
		       .subscribe(item -> logger.info(item.toString()));
		
	}

}
