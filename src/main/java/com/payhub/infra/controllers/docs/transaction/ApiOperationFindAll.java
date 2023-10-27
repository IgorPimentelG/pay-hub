package com.payhub.infra.controllers.docs.transaction;

import com.payhub.domain.entities.Transaction;
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
@Operation(summary = "Find All Transactions", description = "Allow find all transactions", tags = {"Transaction"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "All transactions found", content = @Content(
		array = @ArraySchema(schema = @Schema(implementation = Transaction.class))
	))
})
public @interface ApiOperationFindAll {}
