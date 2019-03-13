package com.curso.springboot.webflux.app.model.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.curso.springboot.webflux.app.model.documents.Product;
@Repository
public interface IProductDAO extends ReactiveMongoRepository<Product, String>{

}
