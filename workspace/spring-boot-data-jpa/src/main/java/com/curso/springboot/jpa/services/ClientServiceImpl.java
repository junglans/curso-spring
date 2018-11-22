package com.curso.springboot.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.curso.springboot.jpa.models.dao.IBasicDAO;
import com.curso.springboot.jpa.models.dao.IClientDAO;
import com.curso.springboot.jpa.models.dto.ClientDTO;
import com.curso.springboot.jpa.models.entity.ClientEntity;
import com.curso.springboot.jpa.utils.MapperUtil;

@Service
public class ClientServiceImpl implements IClientService {

	@Autowired
	protected MapperUtil mapper;

	@Autowired
	// Pasamos de la implementación basada en la implementación de DAO's a usar
	// Spring DATA
	// protected IBasicDAO<ClientEntity> clientDao;
	protected IClientDAO clientDao;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<ClientDTO> findAll() {
		return mapper.map(clientDao.findAll(), ClientDTO.class);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ClientDTO save(ClientDTO dto) {
		return mapper.map(clientDao.save(mapper.map(dto, ClientEntity.class)), ClientDTO.class);
	}

	@Transactional(readOnly = true,propagation = Propagation.REQUIRED)
	public ClientDTO findById(Long id) {
		return mapper.map(clientDao.findById(id).orElse(null), ClientDTO.class);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ClientDTO fetchByIdWithInvoices(Long id) {
		return mapper.map(clientDao.fetchByIdWithInvoices(id), ClientDTO.class);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		clientDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Page<ClientDTO> findAll(Pageable pageable) {

		Page<ClientEntity> pageEntity = clientDao.findAll(pageable);
		List<ClientDTO> listDto = mapper.map(pageEntity, ClientDTO.class);
		PageImpl<ClientDTO> pageDto = new PageImpl<ClientDTO>(listDto, pageable, pageEntity.getTotalElements());
		return pageDto;

	}

}
