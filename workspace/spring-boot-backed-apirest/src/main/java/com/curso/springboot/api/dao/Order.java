package com.curso.springboot.api.dao;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Order {
	ASCENDING("ASC"), DESCENDING("DESC");
	
	private String order;
	 
    Order(String order) {
        this.order = order;
    }

    @JsonValue
	public String getOrder() {
		return order;
	}

}
