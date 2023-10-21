package com.payhub.infra.mocks;

import com.payhub.domain.entities.AccountVerification;
import com.payhub.domain.entities.Client;
import com.payhub.domain.types.VerificationMethod;

import java.time.LocalDateTime;

public abstract class AccountVerificationMock {
	public static AccountVerification createEntity(VerificationMethod method) {
		return new AccountVerification(
			"any code",
			ClientMock.createEntity(),
			LocalDateTime.now().plusHours(1),
			method
		);
	}

	public static AccountVerification createEntity(Client client, VerificationMethod method) {
		return new AccountVerification(
			"any code",
			client,
			LocalDateTime.now().plusHours(1),
			method
		);
	}
}
