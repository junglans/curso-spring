package com.curso.springboot.jpa.auth.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.curso.springboot.jpa.auth.SimpleGrantedAuthoritiesMixIn;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		if (!requiresAuthentication(request)) {
			chain.doFilter(request, response);
			return;
		}
		boolean validToken = false;
		Claims token = null;
		try {
	 
			token = Jwts.parser()
					.setSigningKey("{Esta.es.mi.clave.de.cifrado-1234567890123456789012345678901234567890}".getBytes())
					.parseClaimsJws(request.getHeader("Authorization").replaceAll("Bearer ", ""))
					.getBody();
			
			validToken = true;
			
		} catch (JwtException | IllegalArgumentException e) {
			logger.error(e);
		} 
		// Esto es la sesión
		UsernamePasswordAuthenticationToken authentication = null;
		if (validToken) {
			
			String username = token.getSubject();
			Object roles = token.get("authorities");
			
			Collection<? extends GrantedAuthority> authorities = 
					Arrays.asList( new ObjectMapper()
							       .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesMixIn.class)
								   .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
			authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		return header != null && header.toLowerCase().startsWith("bearer ");
	}

}
