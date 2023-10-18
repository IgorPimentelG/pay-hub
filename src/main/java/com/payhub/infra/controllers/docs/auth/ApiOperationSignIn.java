package com.payhub.infra.controllers.docs.auth;

import com.payhub.infra.dtos.credentials.AuthDto;
import com.payhub.infra.errors.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Sign In", description = "Allow to authenticate the client", tags = {"Auth"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The client has been authenticated", content = @Content(
		schema = @Schema(implementation = AuthDto.class)
	)),
	@ApiResponse(responseCode = "403", description = "Credentials are invalid", content = @Content(
		schema = @Schema(implementation = UnauthorizedException.class)
	))
})
public @interface ApiOperationSignIn {}
