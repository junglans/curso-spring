package com.curso.springboot.jpa.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoritiesMixIn {

	@JsonCreator
	public SimpleGrantedAuthoritiesMixIn(@JsonProperty("authority") String role) {
		super();
	}

}
