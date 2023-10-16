package com.payhub.main.configs.secutiry;

import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.repositories.ClientRepository;
import com.payhub.infra.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService service;

	@Autowired
	private ClientRepository repository;

	@Override
	protected void doFilterInternal(
	  HttpServletRequest request,
	  HttpServletResponse response,
	  FilterChain chain
	) throws ServletException, IOException {
		var token = getToken(request);

		if (token != null) {
			var subject = service.validateToken(token);
			var client = repository.findByEmail(subject)
			  .orElseThrow(() -> new NotFoundException("The client doesn't exist."));
			var auth = new UsernamePasswordAuthenticationToken(client, null);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		return authHeader == null ? null : authHeader.replace("Bearer ", "").trim();
	}
}
