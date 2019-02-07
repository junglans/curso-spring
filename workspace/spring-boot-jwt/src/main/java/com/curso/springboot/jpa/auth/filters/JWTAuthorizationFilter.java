package com.curso.springboot.jpa.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.curso.springboot.jpa.auth.service.JWTService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTService jwtService;
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		if (!requiresAuthentication(request)) {
			chain.doFilter(request, response);
			return;
		}
		
		 
		UsernamePasswordAuthenticationToken authentication = null;
		
		String jwtToken = jwtService.resolveToken(request.getHeader(JWTService.HEADER_STRING));
		if (jwtService.validateToke(jwtToken)) {
			authentication = new UsernamePasswordAuthenticationToken(jwtService.getClaims(jwtToken).getSubject(), null, jwtService.getAuthorities(jwtToken));
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	protected boolean requiresAuthentication(HttpServletRequest request) {
		String header = request.getHeader(JWTService.HEADER_STRING);
		return header != null && header.startsWith(JWTService.TOKEN_PREFIX);
	}

}
