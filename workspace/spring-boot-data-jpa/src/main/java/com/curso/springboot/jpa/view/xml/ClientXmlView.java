package com.curso.springboot.jpa.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.curso.springboot.jpa.models.bean.ClientBean;

@Component("clients.xml")
public class ClientXmlView extends MarshallingView {

	 
	@Autowired
	public ClientXmlView(Jaxb2Marshaller jaxb2Marshaller) {
		super(jaxb2Marshaller);
		setContentType("text/xml");
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"clients.xml\"");
		response.setContentType(getContentType());
		Page<ClientBean> clients = (Page<ClientBean>) model.get("clientList");
		
		model.clear();
		
		model.put("clients", new ClientList(clients.getContent()));
		super.renderMergedOutputModel(model, request, response);
	}

}
