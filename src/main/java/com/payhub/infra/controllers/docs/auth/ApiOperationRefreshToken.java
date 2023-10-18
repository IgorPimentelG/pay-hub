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
@Operation(summary = "Refresh Token", description = "", tags = {"Auth"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "Access token has been renewed", content = @Content(
		schema = @Schema(implementation = AuthDto.class)
	)),
	@ApiResponse(responseCode = "403", description = "Refresh token is invalid or expired", content = @Content(
		schema = @Schema(implementation = UnauthorizedException.class)
	))
})
public @interface ApiOperationRefreshToken {}
