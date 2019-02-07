package com.curso.springboot.jpa.auth.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.curso.springboot.jpa.auth.service.JWTService;
import com.curso.springboot.jpa.models.bean.UserBean;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	private JWTService jwtService;
	public JWTAuthenticationFilter(AuthenticationManager authManager, JWTService jwtService, AntPathRequestMatcher pathMatcher) {
		super();
		this.authManager = authManager;
		this.jwtService = jwtService;
		// Reemplazamos url la página de login que está por defecto "AntPathRequestMatcher("/login", "POST")" por la nuestra.
		if (pathMatcher != null) {
			setRequiresAuthenticationRequestMatcher(pathMatcher);
		}
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		// Si no vienen como request parameters, buscamos usuario y clave en el body de la petición
		// como un formato json.
		if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
			UserBean user = null;
			try {
				user = new ObjectMapper().readValue(request.getInputStream(), UserBean.class);
				username = user.getUsername();
				password = user.getPassword();
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				throw new AuthenticationServiceException(e.getLocalizedMessage(), e);
			} 
		}
		
		logger.info("Usuario : " + username + ", Password : " + password);
		username = username.trim(); 
		//Este token no es el token de JWT 
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		// Aquí se verifica el login
		return authManager.authenticate(authToken);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		// Contruimos el body que vamos a mandar
		Map<String, Object> body = new HashMap<>();
		body.put("message", "Error de autentificación usuario y/o password incorrecto");
		body.put("error", failed.getLocalizedMessage());
		
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String jwtToken = jwtService.create(authResult);
		// Generamos los encabezados...
		response.addHeader(JWTService.HEADER_STRING, JWTService.TOKEN_PREFIX + jwtToken);
		response.addHeader("Content-Type", "application/json; charset=UTF-8"); // O bien con response.setContentType()
		
		// Contruimos el body que vamos a mandar
		Map<String, Object> body = new HashMap<>();
		body.put("token", jwtToken);
		body.put("user", (User)authResult.getPrincipal());
		body.put("message", String.format("¡Hola %s, has iniciado sesión con éxito!", jwtService.getUserName(jwtToken)));
		// Escribimos el cuerpo de la respuesta.
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		// Indicamos el OK de la petición Http
		response.setStatus(HttpStatus.OK.value());
	
	}

}
