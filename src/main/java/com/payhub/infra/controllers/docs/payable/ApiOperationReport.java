package com.payhub.infra.controllers.docs.payable;

import com.payhub.infra.dtos.transaction.ReportResponse;
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
@Operation(summary = "Generate Report", description = "Allow get a payment report", tags = {"Payable"})
@ApiResponse(responseCode = "200", description = "The report was generated",
	content = @Content(schema = @Schema(implementation = ReportResponse.class)))
public @interface ApiOperationReport {}
