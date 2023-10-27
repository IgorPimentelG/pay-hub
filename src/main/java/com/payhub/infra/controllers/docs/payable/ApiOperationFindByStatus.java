package com.payhub.infra.controllers.docs.payable;

import com.payhub.domain.entities.Payable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Operation(summary = "Find All Payable BY Status", description = "Allow find all payable by status", tags = {"Payable"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The payable was found by status", content = @Content(
		array = @ArraySchema(schema = @Schema(implementation = Payable.class))
	))
})
public @interface ApiOperationFindByStatus {}
