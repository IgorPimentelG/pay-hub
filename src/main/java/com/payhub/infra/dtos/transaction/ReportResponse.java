package com.payhub.infra.dtos.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReportResponse(
	@JsonProperty("total_receivable")
	String totalReceivable,

	@JsonProperty("total_received")
	String totalReceived,

	@JsonProperty("total_transactions")
	int totalTransactions
) {}
