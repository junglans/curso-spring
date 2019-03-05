package com.curso.springboot.api.dao;

import com.fasterxml.jackson.annotation.JsonValue;
public enum OrderType {
	ASCENDING("ASC"), DESCENDING("DESC");
	
	private String order;
	 
    OrderType(String order) {
        this.order = order;
    }

    @JsonValue
	public String getOrder() {
		return order;
	}
 
    
}
