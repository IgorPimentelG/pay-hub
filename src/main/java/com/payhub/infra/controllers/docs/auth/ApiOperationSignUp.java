package com.payhub.infra.controllers.docs.auth;

import com.payhub.domain.entities.Client;
import com.payhub.infra.errors.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Sign Up", description = "Allow register", tags = {"Auth"})
@ApiResponse(responseCode = "200", description = "The client has been registered",
	content = @Content(schema = @Schema(implementation = Client.class)))
@ApiResponse(responseCode = "400", description = "The client data is invalid",
	content = @Content(schema = @Schema(implementation = BadRequestException.class)))
public @interface ApiOperationSignUp {}
