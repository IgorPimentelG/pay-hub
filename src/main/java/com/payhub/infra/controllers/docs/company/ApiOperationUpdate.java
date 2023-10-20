package com.payhub.infra.controllers.docs.company;

import com.payhub.domain.entities.Company;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.ForbiddenException;
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
@Operation(summary = "Update Company", description = "Allow update a company", tags = {"Company"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The company has been updated", content = @Content(
		schema = @Schema(implementation = Company.class)
	)),
	@ApiResponse(responseCode = "400", description = "Data is invalid", content = @Content(
		schema = @Schema(implementation = BadRequestException.class)
	)),
	@ApiResponse(responseCode = "403", description = "The client is not allowed to update the company", content = @Content(
		schema = @Schema(implementation = ForbiddenException.class)
	)),
	@ApiResponse(responseCode = "404", description = "The company was not found", content = @Content(
		schema = @Schema(implementation = NotFoundException.class)
	))
})
public @interface ApiOperationUpdate {}
