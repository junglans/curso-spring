package com.curso.springboot.jpa.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class SimpleFilter extends OncePerRequestFilter {

	protected AntPathRequestMatcher pathMatcher;
	public SimpleFilter(AntPathRequestMatcher pathMatcher) {
		super();
		this.pathMatcher = pathMatcher;
	}
 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Before Simple filter URL: " + request.getRequestURI());
		filterChain.doFilter(request, response);
		logger.info("After Simple filter");
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return pathMatcher.matches(request);
	}

}
