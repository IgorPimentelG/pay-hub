package com.payhub.infra.controllers.docs.company;

import com.payhub.domain.entities.Company;
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
@Operation(summary = "Find Company", description = "Allow find a company", tags = {"Company"})
@ApiResponse(responseCode = "200", description = "The company was found",
	content = @Content(schema = @Schema(implementation = Company.class)))
@ApiResponse(responseCode = "404", description = "The company was not found",
	content = @Content(schema = @Schema(implementation = NotFoundException.class)))
public @interface ApiOperationFind {}
