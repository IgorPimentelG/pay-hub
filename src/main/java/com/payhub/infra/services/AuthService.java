package com.payhub.infra.services;

import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.credentials.*;
import com.payhub.infra.errors.ExpiredVerification;
import com.payhub.infra.errors.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private ClientService clientService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AccountVerificationService accountVerificationService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthDto signIn(LoginDto credentials) {
		try {
			var user = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
			authenticationManager.authenticate(user);

			var client = clientService.findByEmail(credentials.email());

			if (!client.isEnabled()) {
				throw new UnauthorizedException();
			}

			return tokenService.createToken(client);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
	}

	public Client signUp(CreateClientDto data) {
		return clientService.create(data);
	}

	public AuthDto refreshToken(String refreshToken) {
		return tokenService.refreshToken(refreshToken);
	}

	public void activeAccount(String clientId, String code) {
		try {
			accountVerificationService.verifyCode(code, clientId);
			clientService.active(clientId);
		} catch (Exception error) {
			var client = clientService.findById(clientId);

			if (!client.isEnabled()) {
				clientService.sendVerificationCode(client);
				throw new ExpiredVerification();
			}
			throw error;
		}
	}
}
