package com.curso.springboot.api.dao;

import java.io.Serializable;

public class SortBy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060625738774119272L;
	private String attrName;
	private Order order;

	public SortBy(String attrName, Order order) {
		this.attrName = attrName;
		this.order = order;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
