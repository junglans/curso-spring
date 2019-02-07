package com.curso.springboot.jpa.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JWTService {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	/**
	 * Méto que crea el token
	 * @param auth
	 * @return
	 */
	 String create(Authentication auth) throws IOException;
	 boolean validateToke(String token);
	 Claims getClaims(String token);
	 String getUserName(String token);
	 Collection<? extends GrantedAuthority> getAuthorities(String token) throws IOException;
	 String resolveToken(String authorityHeader);
 }
