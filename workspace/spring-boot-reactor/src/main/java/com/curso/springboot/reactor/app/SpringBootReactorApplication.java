package com.curso.springboot.reactor.app;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.curso.springboot.reactor.app.models.User;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(SpringBootReactorApplication.class);
	public static void main(String[] args)  {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Consumer<User> consumer = new Consumer<User> () {

			@Override
			public void accept(User item) {
				System.out.println(item.getName());
			}};
		// Definimos el flujo...
	    Flux<String> names = Flux.just("Andres", "Pedro", "Maria", "Diego", "Juan")
	    		.map(item -> new User(item.toUpperCase(), null))
	    		.doOnNext(consumer)
	    		.map(item -> item.getName().toLowerCase());
	    		 
	    // En la suscripción se inicia el procesado
	    names.subscribe(item -> logger.info("Item:" + item), 
	    				error -> logger.error(error.getMessage()),
	    				new Runnable() {

							@Override
							public void run() {
							   logger.info("Flujo finalizado");
								
							}});
	}

	
}
