package com.curso.springboot.jpa.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.curso.springboot.jpa.models.entity.InvoiceEntity;

public interface IInvoiceDAO  extends CrudRepository<InvoiceEntity, Long>{

}
