package com.curso.springboot.jpa.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.curso.springboot.jpa.models.bean.ClientBean;

@XmlRootElement(name = "clients")
public class ClientList {

	@XmlElement(name = "client")
	private List<ClientBean> clientsList;

	
	public ClientList() {
		super();
	}


	public ClientList(List<ClientBean> clients) {
		super();
		this.clientsList = clients;
	}

 
	public List<ClientBean> getClientsList() {
		return clientsList;
	}

 
	
	
}
