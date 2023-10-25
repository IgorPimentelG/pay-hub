package com.payhub.infra.mocks;

import com.payhub.domain.entities.Transaction;
import com.payhub.domain.types.PaymentMethod;
import com.payhub.infra.dtos.transaction.TransactionDto;

import java.math.BigDecimal;

public abstract class TransactionMock {
	public static Transaction createEntity() {
		return new Transaction(
			"any description",
			BigDecimal.valueOf(100),
      CardMock.createEntity(),
			PaymentMethod.CREDIT_CARD
		);
	}

	public static TransactionDto createDto() {
		return new TransactionDto(
			"any description",
			BigDecimal.valueOf(100),
      "CREDIT_CARD",
      CardMock.createDto()
		);
	}
}
