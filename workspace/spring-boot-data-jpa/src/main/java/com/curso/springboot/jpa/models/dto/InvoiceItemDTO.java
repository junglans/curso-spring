package com.curso.springboot.jpa.models.dto;

import java.io.Serializable;
import java.util.List;

public class InvoiceItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9156756966553514453L;
	private Long id;
	private Integer quantity;
	 
	
	private ProductDTO product;
	private List<InvoiceItemDTO> items;

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

	 

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public List<InvoiceItemDTO> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItemDTO> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		InvoiceItemDTO other = (InvoiceItemDTO) obj;
 
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
