package com.curso.springboot.jpa.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name = "factura_id", insertable = true, updatable=false, nullable=false)
	private Long facturaId;
	
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

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}
	
	
}
