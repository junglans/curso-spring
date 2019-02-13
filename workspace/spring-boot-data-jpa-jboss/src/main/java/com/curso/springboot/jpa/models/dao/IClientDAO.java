package com.curso.springboot.jpa.models.dao;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Implementación del dao de cliente usando Spring Data
 */
import com.curso.springboot.jpa.models.entity.ClientEntity;

public interface IClientDAO extends PagingAndSortingRepository<ClientEntity, Long>{

	@Query("select c from ClientEntity c left join fetch c.invoices f where c.id=?1")
	ClientEntity fetchByIdWithInvoices(Long clientId);
}
