package com.curso.springboot.jpa.view.json;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.AbstractJackson2View;

import com.curso.springboot.jpa.models.bean.ClientBean;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("clients.json")
public class ClientListJsonView extends AbstractJackson2View {

	protected String modelKey;
	protected ClientListJsonView(@Autowired ObjectMapper objectMapper, @Value("application/json") String contentType) {
		super(objectMapper, contentType);
		setModelKey("clientList");  
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"clients.json\"");
		response.setContentType(getContentType());
		super.renderMergedOutputModel(model, request, response);
	}
 
	@Override
	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object filterModel(Map<String, Object> model) {
		Page<ClientBean> beanPage = (Page<ClientBean>)model.get(this.modelKey);
		Map<String, Object> filteredModel = new HashMap<String, Object>();
		filteredModel.put(modelKey, beanPage.getContent());
		return filteredModel;
	}
 
}
