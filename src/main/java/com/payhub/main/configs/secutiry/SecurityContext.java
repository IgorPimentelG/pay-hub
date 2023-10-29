package com.payhub.main.configs.secutiry;

import com.payhub.domain.entities.Client;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext {
	public Client currentUser() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		return (Client) auth.getPrincipal();
	}

	public String getUsername() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	public boolean isAuthenticated() {
    var auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getName().equals("anonymousUser")) {
			return false;
		}
    return auth.isAuthenticated();
  }
}
