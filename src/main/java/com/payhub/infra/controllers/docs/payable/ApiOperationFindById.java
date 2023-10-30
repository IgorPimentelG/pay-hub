package com.payhub.infra.controllers.docs.payable;

import com.payhub.domain.entities.Payable;
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
@Operation(summary = "Find Payable", description = "Allow find a payable", tags = {"Payable"})
@ApiResponse(responseCode = "200", description = "The payable was found",
	content = @Content(schema = @Schema(implementation = Payable.class)))
@ApiResponse(responseCode = "404", description = "The payable could not be found",
	content = @Content(schema = @Schema(implementation = NotFoundException.class)))
public @interface ApiOperationFindById {}
