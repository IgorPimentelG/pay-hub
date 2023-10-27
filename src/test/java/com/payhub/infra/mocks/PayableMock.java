package com.payhub.infra.mocks;

import com.payhub.domain.entities.Payable;
import com.payhub.domain.entities.Transaction;
import com.payhub.domain.types.PaymentStatus;

import java.time.LocalDateTime;

public abstract class PayableMock {
	public static Payable createEntity(Transaction transaction) {
		return new Payable(
			transaction,
			ClientMock.createEntity(),
      LocalDateTime.now(),
      PaymentStatus.PAID
		);
	}

	public static Payable createEntity() {
		return new Payable(
			TransactionMock.createEntity(),
			ClientMock.createEntity(),
			LocalDateTime.now(),
			PaymentStatus.PAID
		);
	}
}
