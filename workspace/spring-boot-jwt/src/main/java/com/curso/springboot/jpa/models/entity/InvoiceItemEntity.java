package com.curso.springboot.jpa.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="INVOICES_ITEMS")
public class InvoiceItemEntity extends Identifiable<Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -872651874980492451L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(insertable = true, updatable=true, nullable=false)
	private Integer quantity;
	//@Column(name = "factura_id", insertable = true, updatable=false, nullable=false)
	//private Long facturaId;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn (name = "product_id")
	private ProductEntity product;
	
	@Override
	public Long getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double calculateTotal() {
		return null;
	}

//	public Long getFacturaId() {
//		return facturaId;
//	}
//
//	public void setFacturaId(Long facturaId) {
//		this.facturaId = facturaId;
//	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((facturaId == null) ? 0 : facturaId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		InvoiceItemEntity other = (InvoiceItemEntity) obj;
//		if (facturaId == null) {
//			if (other.facturaId != null)
//				return false;
//		} else if (!facturaId.equals(other.facturaId))
//			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
	
}
