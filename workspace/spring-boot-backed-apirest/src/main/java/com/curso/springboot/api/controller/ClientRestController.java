package com.curso.springboot.api.controller;

import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.curso.springboot.api.entity.ClientEntity;
import com.curso.springboot.api.services.IClientService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value="/api")
public class ClientRestController extends BaseCRUDRestControler<ClientEntity> {
	
	public ClientRestController(@Autowired IClientService service) {
		super(service);
	}

	@RequestMapping(value="/client", method=RequestMethod.GET)
	public ResponseEntity<?> findAll() throws Exception {
		return super.findAll();
	}
	
	@RequestMapping(value="/client/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable(value="id", required = true) Long id) throws Exception {
		return super.get(id);
	}
	
	@RequestMapping(value="/client", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>  create(@RequestBody(required = true) ClientEntity client) throws Exception {
		return super.create(client);
	}
	
	@RequestMapping(value="/client/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody(required=true) ClientEntity entity, @PathVariable(name = "id") Long id) throws Exception {
		
			// Este es el manejador de la modificacion.
		 	BiConsumer<ClientEntity, ClientEntity> fn = (current, client) -> {
		 		current.setName(client.getName());
		 		current.setEmail(client.getEmail());
		 		current.setSurname(client.getSurname());
		 	};
			return super.update(entity, id, fn);
	 
	}
	
	@RequestMapping(value="/client/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
	    return super.delete(id);
	}
}
