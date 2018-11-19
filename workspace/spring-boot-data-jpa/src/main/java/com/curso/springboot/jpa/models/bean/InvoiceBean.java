package com.curso.springboot.jpa.models.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class InvoiceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044685575013990415L;

	private Long id;
	
	@NotEmpty
	private String description;
	@NotEmpty
	private String observations;

	private Date creationDate;

	private ClientBean client;

	private List<InvoiceItemBean> items;

	public InvoiceBean() {
		super();
		this.items = new ArrayList<InvoiceItemBean>();
	}

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

	public ClientBean getClient() {
		return client;
	}

	public void setClient(ClientBean client) {
		this.client = client;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<InvoiceItemBean> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItemBean> items) {
		this.items = items;
	}

	public void addItem(InvoiceItemBean item) {
		this.items.add(item);
	}

	public Double getTotal() {
		Double total = 0.0;
		for (InvoiceItemBean item : items) {
			total += item.getTotalItem();
		}
		return total;
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
		InvoiceBean other = (InvoiceBean) obj;
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
