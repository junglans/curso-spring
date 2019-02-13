package com.curso.springboot.jpa.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.curso.springboot.jpa.models.bean.ClientBean;

@Component("clients.csv")
public class ClientCsvView extends AbstractView {

	public ClientCsvView() {
		super();
		setContentType("text/csv");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
		response.setContentType(getContentType());


		Page<ClientBean> clients = (Page<ClientBean>) model.get("clientList");
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(),CsvPreference.STANDARD_PREFERENCE);
		
		String[] headers = {"id", "name", "surname", "email", "creationDate"};
		beanWriter.writeHeader(headers);
		
		for (ClientBean client : clients ) {
			beanWriter.write(client,  headers);
		}
		beanWriter.close();
	}

}
