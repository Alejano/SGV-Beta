package com.PGJ.SGV.service;


import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class IObtenerUserServiceImpl implements IObtenerUserService  {

	public String obtenUser() {
	
	String user ="";
	
	if(hasRole("ROLE_ADMIN")) {
		user ="ROLE_ADMIN";
		}else {
			if(hasRole("ROLE_USER")) {
				user = "ROLE_USER";
			}else {
				if(hasRole("ROLE_SEGURO")) {
					user = "ROLE_SEGURO";
				}else {
					if(hasRole("ROLE_TALLER")) {
						user = "ROLE_TALLER";
					}else {
						if(hasRole("ROLE_SINIESTRO")) {
							user = "ROLE_SINIESTRO"; 
						}
					}
				}
			}	
		}
	System.err.println(user);
	return user;
	}
	
	public boolean hasRole (String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context==null) {
		return false;
		}
		
		Authentication auth = context.getAuthentication();
	
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> autorithies = auth.getAuthorities();
		for(GrantedAuthority authority: autorithies) {
			if(role.equals(authority.getAuthority())) {return true;}
		}
		
		return false;
		}
	
}


