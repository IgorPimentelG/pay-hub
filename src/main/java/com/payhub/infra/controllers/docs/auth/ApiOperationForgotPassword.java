package com.payhub.infra.controllers.docs.auth;

import com.payhub.infra.dtos.global.InformativeDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Forgot Password", description = "Allow request account recovery", tags = {"Auth"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The request has been registered", content = @Content(
		schema = @Schema(implementation = InformativeDto.class)
	)),
	@ApiResponse(responseCode = "404", description = "Client doesn't exist", content = @Content(
		schema = @Schema(implementation = NotFoundException.class)
	))
})
public @interface ApiOperationForgotPassword {}
