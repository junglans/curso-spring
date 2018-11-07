package com.curso.springboot.jpa.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.curso.springboot.jpa.models.bean.ProductBean;
import com.curso.springboot.jpa.services.IProductService;
import com.curso.springboot.jpa.utils.MapperUtil;

@Controller
@RequestMapping("products")
public class ProductController {
	@Autowired
	private MapperUtil mapper;
	
	@Autowired
	private IProductService service;
	
	@RequestMapping(value = "/load-products/{term}", produces= {"aplication/json"}, method = RequestMethod.GET)
	public @ResponseBody List<ProductBean> getProductsByName(@PathVariable(value = "term") String term) {
		// Utilizamos ResponseBody por que este controlador no va a disparar la navegación a una vista de Thymeleave
		// Va a incluir la lista de productos dentro del response como un json.
		return mapper.map(service.findByName(term), ProductBean.class);
	}

}
