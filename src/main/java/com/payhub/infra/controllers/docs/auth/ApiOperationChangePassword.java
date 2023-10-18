package com.payhub.infra.controllers.docs.auth;

import com.payhub.infra.dtos.global.InformativeDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.ExpiredVerification;
import com.payhub.infra.errors.FailVerification;
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
@Operation(summary = "Change Password", description = "Allow set new password", tags = {"Auth"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "Password has been changed", content = @Content(
		schema = @Schema(implementation = InformativeDto.class)
	)),
	@ApiResponse(responseCode = "400", description = "Password is invalid", content = @Content(
		schema = @Schema(implementation = BadRequestException.class)
	)),
	@ApiResponse(responseCode = "404", description = "Client doesn't exist", content = @Content(
		schema = @Schema(implementation = NotFoundException.class)
	)),
	@ApiResponse(responseCode = "409", description = "The code doesn't match", content = @Content(
		schema = @Schema(implementation = FailVerification.class)
	)),
	@ApiResponse(responseCode = "409", description = "The code has expired", content = @Content(
		schema = @Schema(implementation = ExpiredVerification.class)
	))
})
public @interface ApiOperationChangePassword {}
