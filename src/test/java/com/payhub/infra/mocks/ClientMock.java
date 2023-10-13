package com.payhub.infra.mocks;

import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.client.UpdateClientDto;

public abstract class ClientMock {
	public static Client createEntity() {
		return new Client(
			"any@mail.com",
			"any password",
			"any cpf",
			"any full name"
		);
	}

	public static CreateClientDto createDto() {
		return new CreateClientDto(
			"any@mail.com",
			"any password",
			"any cpf",
			"any full name"
		);
	}

	public static UpdateClientDto updateDto() {
		return new UpdateClientDto(
			"any password updated"
		);
	}
}
