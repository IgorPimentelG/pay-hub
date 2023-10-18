package com.payhub.infra.controllers;

import com.payhub.domain.entities.Client;
import com.payhub.infra.controllers.docs.auth.*;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.client.UpdateClientDto;
import com.payhub.infra.dtos.credentials.AuthDto;
import com.payhub.infra.dtos.credentials.LoginDto;
import com.payhub.infra.dtos.global.InformativeDto;
import com.payhub.infra.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

	@Autowired
	private AuthService service;

	@ApiOperationSignIn
	@PostMapping("/sign-in")
	public ResponseEntity<AuthDto> signIn(@RequestBody @Valid LoginDto login) {
		var result = service.signIn(login);
		return ResponseEntity.ok(result);
	}

	@ApiOperationSignUp
	@PostMapping("/sign-up")
	public ResponseEntity<Client> signUp(@RequestBody @Valid CreateClientDto data) {
		var result = service.signUp(data);
		return ResponseEntity.ok(result);
	}

	@ApiOperationRefreshToken
	@GetMapping("/refresh/{token}")
	public ResponseEntity<AuthDto> refreshToken(@PathVariable("token") String token) {
		var result = service.refreshToken(token);
		return ResponseEntity.ok(result);
	}

	@ApiOperationActiveAccount
	@PatchMapping("/active-account/{clientId}/{code}")
	public ResponseEntity<InformativeDto> activeAccount(
		@PathVariable("clientId") String clientId,
		@PathVariable("code") String code
	) {
		service.activeAccount(clientId, code);
		return ResponseEntity.ok(new InformativeDto("Your account is active."));
	}

	@ApiOperationForgotPassword
	@PostMapping("/forgot-password/{email}")
	public ResponseEntity<InformativeDto> accountRecovery(@PathVariable("email") String email) {
		service.accountRecovery(email);
		return ResponseEntity.ok(new InformativeDto("Verify your email."));
	}

	@ApiOperationChangePassword
	@PatchMapping("/change-password/{clientId}/{code}")
	public ResponseEntity<InformativeDto> changePassword(
    @PathVariable("clientId") String clientId,
    @PathVariable("code") String code,
    @RequestBody @Valid UpdateClientDto data
  ) {
		service.changePassword(clientId, code, data);
		return ResponseEntity.ok(new InformativeDto("Your password was changed."));
	}
}
