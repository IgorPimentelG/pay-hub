package com.payhub.infra.dtos.transaction;

import java.time.LocalDateTime;

public record PayableResponse(
	String id,
	LocalDateTime paymentDate,
	String status,
	TransactionResponse transaction
) {}
