package com.curso.springboot.api.dao;

import java.io.Serializable;

public class SortBy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8060625738774119272L;
	private String attrName;
	private OrderType order;

	public SortBy(String attrName, OrderType order) {
		this.attrName = attrName;
		this.order = order;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public OrderType getOrder() {
		return order;
	}

	public void setOrder(OrderType order) {
		this.order = order;
	}

}
