package com.curso.springboot.jpa.models.dao;

 

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.curso.springboot.jpa.models.entity.ProductEntity;

public interface IProductDAO  extends CrudRepository<ProductEntity, Long>{

	@Query("select p from ProductEntity p where p.name like %?1%")
	public List<ProductEntity> findByName(String term);
	public List<ProductEntity> findByNameLikeIgnoreCase(String term);
}
