package com.payhub.infra.controllers.docs.address;

import com.payhub.domain.entities.Address;
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

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(summary = "Find Address", description = "Allow find an address", tags = {"Address"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The address was found", content = @Content(
		schema = @Schema(implementation = Address.class)
	)),
	@ApiResponse(responseCode = "404", description = "The address doesn't exist", content = @Content(
		schema = @Schema(implementation = NotFoundException.class)
	))
})
public @interface ApiOperationFind {}
