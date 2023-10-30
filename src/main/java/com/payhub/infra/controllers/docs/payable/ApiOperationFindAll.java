package com.payhub.infra.controllers.docs.payable;

import com.payhub.domain.entities.Payable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Find All Payable", description = "Allow find all payable", tags = {"Payable"})
@ApiResponse(responseCode = "200", description = "The payable was found",
	content = @Content(array = @ArraySchema(schema = @Schema(implementation = Payable.class))))
public @interface ApiOperationFindAll {}
