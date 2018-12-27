package com.jwtme.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 10 dias (860_000_000)
	static final long EXPIRATION_TIME = 300_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static void addAuthentication(HttpServletResponse response, Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String tipo = "";
		for (GrantedAuthority grantedAuthority : authorities) {
			if("ADMIN".equals(grantedAuthority.getAuthority())) {
				tipo = "admin";
			}
		}
		
		String JWT = Jwts.builder()
				.setSubject(auth.getName())
				.claim("tipo", tipo)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null && !"".equals(token)) {
			// faz parse do token
			try {
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
				
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
				}
			}catch (JwtException e) {
				return null;
			}
		}
		return null;
	}
	
	static Authentication getAdminAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if (token != null && !"".equals(token)) {
			try {
				// faz parse do token
				String user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
				
				
				if (user != null) {
					String tipo = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().get("tipo").toString();
					System.out.println(tipo);
					
					if("admin".equalsIgnoreCase(tipo)) {
						return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
					}else {
						return null;
					}
				}
			}catch (JwtException e) {
				return null;
			}
		}
		return null;
	}
	
}
