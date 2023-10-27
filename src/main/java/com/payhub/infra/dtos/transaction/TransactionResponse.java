package com.payhub.infra.dtos.transaction;

import java.math.BigDecimal;

public record TransactionResponse(
	String description,
	BigDecimal amount,
	String paymentMethod,
	CardResponse card
) {}
