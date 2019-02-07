package com.curso.springboot.jpa.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.curso.springboot.jpa.auth.SimpleGrantedAuthorityMixIn;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTServiceImpl implements JWTService {

	private static final String SECRET_KEY = Base64Utils.encodeToString("{Esta.es.mi.clave.de.cifrado-1234567890123456789012345678901234567890}".getBytes());
	private static final long EXPIRATION_DATE = 4*60*60*1000L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTServiceImpl.class);
	@SuppressWarnings("deprecation")
	@Override
	public String create(Authentication auth) throws IOException {
		 
		String userName = ((User)auth.getPrincipal()).getUsername();
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		
		Long now = System.currentTimeMillis();
		
		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		claims.put("sub", userName);
		claims.put("iat", new Date(now));
		claims.put("exp", new Date(now +EXPIRATION_DATE));
		
		String jwtToken = Jwts.builder()
							.setSubject( userName )
							.signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
							.setIssuedAt(new Date(now))
							.setExpiration(new Date(now + EXPIRATION_DATE) ) // 4 horas
							.setClaims(claims)
							.compact();
		return jwtToken;
	}

	@Override
	public boolean validateToke(String token) {
		try {
			
			getClaims(token);
			return true;
			
		} catch (JwtException | IllegalArgumentException e) {
			LOGGER.error(e.getLocalizedMessage(),e);
			return false;
		} 
		
	}

	@Override
	public Claims getClaims(String token) {
		 
		return Jwts.parser()
				.setSigningKey(SECRET_KEY.getBytes())
				.parseClaimsJws(token.replaceAll(TOKEN_PREFIX, ""))
				.getBody();
	}

	@Override
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(String token) throws IOException {
		 
		Object roles = getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.asList( new ObjectMapper()
						       .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixIn.class)
							   .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

	@Override
	public String resolveToken(String authorityHeader) {
		return authorityHeader.replaceAll(TOKEN_PREFIX, "");
	}

}
