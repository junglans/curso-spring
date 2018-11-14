package com.curso.springboot.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 
import com.curso.springboot.jpa.models.dao.IProductDAO;
import com.curso.springboot.jpa.models.dto.ProductDTO;
import com.curso.springboot.jpa.utils.MapperUtil;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDAO dao;
	
	@Autowired
	protected MapperUtil mapper;

	@Override
	public List<ProductDTO> findByName(String term) {
		return mapper.map(this.dao.findByNameLikeIgnoreCase("%" + term + "%"), ProductDTO.class);
	}

}
