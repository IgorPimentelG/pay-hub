package com.payhub.infra.controllers.docs.auth;

import com.payhub.infra.dtos.global.InformativeDto;
import com.payhub.infra.errors.ExpiredVerification;
import com.payhub.infra.errors.FailVerification;
import com.payhub.infra.errors.NotFoundException;
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
@Operation(summary = "Active Account", description = "Allow the client to activate their account", tags = {"Auth"})
@ApiResponse(responseCode = "200", description = "Account has been activated",
	content = @Content(schema = @Schema(implementation = InformativeDto.class)))
@ApiResponse(responseCode = "404", description = "User not found",
	content = @Content(schema = @Schema(implementation = NotFoundException.class)))
@ApiResponse(responseCode = "409", description = "The code doesn't match",
	content = @Content(schema = @Schema(implementation = FailVerification.class)))
@ApiResponse(responseCode = "409", description = "The code has expired",
	content = @Content(schema = @Schema(implementation = ExpiredVerification.class)))
public @interface ApiOperationActiveAccount {}
