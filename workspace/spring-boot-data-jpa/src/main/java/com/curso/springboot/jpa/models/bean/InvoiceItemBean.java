package com.curso.springboot.jpa.models.bean;

import java.io.Serializable;

public class InvoiceItemBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9156756966553514453L;
	private Long id;
	private Integer quantity;
	private Long facturaId;

	private ProductBean product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}

	public ProductBean getProduct() {
		return product;
	}

	public void setProduct(ProductBean product) {
		this.product = product;
	}

	public Double getTotalItem() {
		return (product != null) ? quantity * product.getPrice() : 0.0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facturaId == null) ? 0 : facturaId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceItemBean other = (InvoiceItemBean) obj;
		if (facturaId == null) {
			if (other.facturaId != null)
				return false;
		} else if (!facturaId.equals(other.facturaId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

}
