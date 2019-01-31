package com.curso.springboot.jpa.auth.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authManager, AntPathRequestMatcher pathMatcher) {
		super();
		this.authManager = authManager;
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

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}
		
		logger.info("Usuario : " + username + ", Password : " + password);
		username = username.trim(); 
		//Este token no es el token de JWT 
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String userName = ((User)authResult.getPrincipal()).getUsername();
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		
		String jwtToken = Jwts.builder()
							.setSubject( userName )
							.signWith(key)
							.compact();
		
		// Generamos los encabezados...
		response.addHeader("Authorization", "Bearer " + jwtToken);
		response.addHeader("Content-Type", "application/json; charset=UTF-8"); // O bien con response.setContentType()
		
		// Contruimos el body que vamos a mandar
		Map<String, Object> body = new HashMap<>();
		body.put("token", jwtToken);
		body.put("user", (User)authResult.getPrincipal());
		body.put("message", String.format("¡Hola %s, has iniciado sesión con éxito!", userName));
		// Escribimos el cuerpo de la respuesta.
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		// Indicamos el OK de la petición Http
		response.setStatus(HttpStatus.OK.value());
	
	}

}
