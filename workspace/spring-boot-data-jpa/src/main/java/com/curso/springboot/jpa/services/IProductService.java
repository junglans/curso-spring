package com.curso.springboot.jpa.services;

import java.util.List;

import com.curso.springboot.jpa.models.dto.ProductDTO;

public interface IProductService {
	List<ProductDTO> findByName(String term);
}
