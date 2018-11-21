package com.curso.springboot.jpa.services;

import com.curso.springboot.jpa.models.dto.InvoiceDTO;

public interface IInvoiceService {

	void saveInvoice(InvoiceDTO invoice);
	
	InvoiceDTO findInvoiceById(Long id);
	
	void deleteInvoice(Long id);
	
	InvoiceDTO fetchByIdWithClientWithInvoiceItemWithProduct(Long id);
}
