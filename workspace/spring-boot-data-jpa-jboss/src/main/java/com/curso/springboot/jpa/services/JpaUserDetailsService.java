package com.curso.springboot.jpa.services;

import java.util.ArrayList;
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.curso.springboot.jpa.models.dao.IUserDAO;
import com.curso.springboot.jpa.models.entity.UserEntity;
@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDetailsService.class);
	@Autowired
	private IUserDAO userDAO;
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		UserEntity user = userDAO.findByUsername(username);
		if (user == null) {
			LOGGER.error("Error en login: No existe el usuario '" + username + "'");
			throw new UsernameNotFoundException("Error en login: No existe el usuario '" + username + "'");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoles().stream().forEach((role) -> {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		});
		
		if (authorities.isEmpty()) {
			LOGGER.error("Error en login: El usuario '" + username + "' no tiene roles asignados.");
			throw new UsernameNotFoundException("Error en login: El usuario '" + username + "' no tiene roles asignados.");
		}
		User userDetails = new User(user.getUsername(),user.getPassword(),user.getEnabled(),true, true, true, authorities);
		return userDetails;
	}

}
