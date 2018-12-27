package com.jwtme.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class CustomFilter extends GenericFilterBean {
 
	List<String> urlsRestritasAoAdmin = new ArrayList<String>();
	List<String> urlsRestritas = new ArrayList<String>();
	
	public CustomFilter() {
		urlsRestritasAoAdmin.add("/restrito");
		urlsRestritas.add("/users");
	}
	
	@Override
    public void doFilter(
      ServletRequest request, 
      ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
		
		String url = ((HttpServletRequest)request).getServletPath().toString();
		System.out.println(url);
		
		if(urlsRestritasAoAdmin.contains(url)) {
			Authentication authentication = TokenAuthenticationService.getAdminAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}else {
			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
        chain.doFilter(request, response);
    }
}