package com.payhub.infra.controllers;

import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.credentials.*;
import com.payhub.infra.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/sign-in")
	public ResponseEntity<AuthDto> signIn(@RequestBody @Valid LoginDto login) {
		var result = service.signIn(login);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Client> signUp(@RequestBody @Valid CreateClientDto data) {
		var result = service.signUp(data);
		return ResponseEntity.ok(result);
	}

	@PostMapping("/refresh/{token}")
	public ResponseEntity<AuthDto> refreshToken(@PathVariable("token") String token) {
		var result = service.refreshToken(token);
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/active-account/{clientId}/{code}")
	public ResponseEntity<?> activeAccount(
		@PathVariable("clientId") String clientId,
		@PathVariable("code") String code
	) {
		service.activeAccount(clientId, code);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
