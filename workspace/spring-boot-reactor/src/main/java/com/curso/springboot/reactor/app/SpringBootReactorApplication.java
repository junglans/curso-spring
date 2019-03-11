package com.curso.springboot.reactor.app;

 
import java.util.Arrays;
import java.util.List;
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
		
		List<String> users = Arrays.asList(new String[] {"Andres Perez", "Bruce Banner", "Maria Casado", "Diego López", "Juan Jiménez", "Bruce Lee", "Bruce Willis"});
		
		Consumer<User> consumer = new Consumer<User> () {

			@Override
			public void accept(User item) {
				System.out.println(item.getName());
			}};
		// Definimos el flujo...
	    //Flux<String> names = Flux.just("Andres Perez", "Bruce Banner", "Maria Casado", "Diego López", "Juan Jiménez", "Bruce Lee", "Bruce Willis");
			
		Flux<String> names = Flux.fromIterable(users).map(item -> { 
	    			String [] data = item.split(" ");
	    			return new User(data[0], data[1]);
	    		   })
	    		.filter(item -> "Bruce".equals(item.getName()))
	    		.doOnNext(consumer)
	    		.map(item -> new User(item.getName().toUpperCase(), item.getSurname().toUpperCase()).toString());
	    		 
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
