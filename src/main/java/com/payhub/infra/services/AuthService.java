package com.payhub.infra.services;

import com.payhub.domain.entities.Client;
import com.payhub.domain.types.VerificationMethod;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.client.UpdateClientDto;
import com.payhub.infra.dtos.credentials.AuthDto;
import com.payhub.infra.dtos.credentials.LoginDto;
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

	public void accountRecovery(String email) {
		var client = clientService.findByEmail(email);
		clientService.sendVerificationCode(client, VerificationMethod.RECOVERY);
	}

	public void changePassword(String clientId, String code, UpdateClientDto data) {
		accountVerificationService.verifyCode(code, clientId, VerificationMethod.RECOVERY);
		clientService.update(data, clientId);
	}

	public void activeAccount(String clientId, String code) {
		try {
			accountVerificationService.verifyCode(code, clientId, VerificationMethod.ACTIVATION);
			clientService.active(clientId);
		} catch (ExpiredVerification error) {
			var client = clientService.findById(clientId);
			if (!client.isEnabled()) {
				clientService.sendVerificationCode(client, VerificationMethod.ACTIVATION);
			}
			throw error;
		}
	}
}
