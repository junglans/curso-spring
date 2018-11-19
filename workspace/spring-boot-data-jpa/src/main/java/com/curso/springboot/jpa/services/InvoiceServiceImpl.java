package com.curso.springboot.jpa.services;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.springboot.jpa.models.dao.IInvoiceDAO;
import com.curso.springboot.jpa.models.dto.InvoiceDTO;
import com.curso.springboot.jpa.models.entity.InvoiceEntity;
import com.curso.springboot.jpa.utils.MapperUtil;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class InvoiceServiceImpl implements IInvoiceService {

	@Autowired
	private IInvoiceDAO dao;
	
	@Autowired
	private MapperUtil mapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveInvoice(InvoiceDTO invoice) {
		dao.save(mapper.map(invoice, InvoiceEntity.class));
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public InvoiceDTO findInvoiceById(Long id) {
		return mapper.map(dao.findOne(id), InvoiceDTO.class);
	}
}
