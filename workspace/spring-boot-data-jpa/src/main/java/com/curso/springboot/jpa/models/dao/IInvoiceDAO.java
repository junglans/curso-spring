package com.curso.springboot.jpa.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.curso.springboot.jpa.models.entity.InvoiceEntity;

public interface IInvoiceDAO  extends CrudRepository<InvoiceEntity, Long>{

	@Query("select f from InvoiceEntity f join fetch f.client c join fetch f.items l join fetch l.product p where f.id=?1")
	InvoiceEntity fetchByIdWithClientWithInvoiceItemWithProduct(Long id);
}
