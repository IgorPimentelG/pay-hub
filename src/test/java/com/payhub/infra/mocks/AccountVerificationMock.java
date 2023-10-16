package com.payhub.infra.mocks;

import com.payhub.domain.entities.AccountVerification;

import java.time.LocalDateTime;

public abstract class AccountVerificationMock {
	public static AccountVerification createEntity() {
		return new AccountVerification(
			"any code",
			ClientMock.createEntity(),
			LocalDateTime.now().plusHours(1)
		);
	}
}
