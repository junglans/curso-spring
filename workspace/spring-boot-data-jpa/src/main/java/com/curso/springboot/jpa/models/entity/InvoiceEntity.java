package com.curso.springboot.jpa.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name = "INVOICES")
public class InvoiceEntity extends Identifiable<Long> implements Serializable {

	 
	private static final long serialVersionUID = 6187703730907048405L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(insertable=true, updatable=true, nullable=false)
	private String description;
	
	@Column(insertable=true, updatable=true, nullable=true)
	private String observations;
	
	@Column(name="creation_date", insertable=true, updatable=false, nullable=false)
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ClientEntity client;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "factura_id", referencedColumnName = "id", nullable = false)
	private List<InvoiceItemEntity> items;
	
	public InvoiceEntity() {
		super();
		this.items = new ArrayList<InvoiceItemEntity>();
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = new Date();
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getObservations() {
		return observations;
	}
	
	public void setObservations(String observations) {
		this.observations = observations;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public ClientEntity getClient() {
		return client;
	}
	
	public void setClient(ClientEntity client) {
		this.client = client;
	}
	
	public List<InvoiceItemEntity> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItemEntity> items) {
		this.items = items;
	}
	
	public void addItem(InvoiceItemEntity item) {
		this.items.add(item);
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		InvoiceEntity other = (InvoiceEntity) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
