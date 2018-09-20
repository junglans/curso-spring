package com.curso.springboot.jpa.models.dao;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Implementación del dao de cliente usando Spring Data
 */
import com.curso.springboot.jpa.models.entity.ClientEntity;

public interface IClientDAO extends JpaRepository<ClientEntity, Long>{

}
