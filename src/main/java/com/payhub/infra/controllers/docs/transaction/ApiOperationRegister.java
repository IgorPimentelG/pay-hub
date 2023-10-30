package com.payhub.infra.controllers.docs.transaction;

import com.payhub.domain.entities.Transaction;
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
@Operation(summary = "Register Transaction", description = "Allow register a transaction", tags = {"Transaction"})
@ApiResponse(responseCode = "200", description = "The transaction has been register",
	content = @Content(schema = @Schema(implementation = Transaction.class)))
@ApiResponse(responseCode = "400", description = "The transaction failed",
	content = @Content(schema = @Schema(implementation = BadRequestException.class)))
public @interface ApiOperationRegister {}
