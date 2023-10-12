package com.payhub.infra.mocks;

import com.payhub.domain.entities.Address;
import com.payhub.infra.dtos.client.CreateAddressDto;
import com.payhub.infra.dtos.client.UpdateAddressDto;

public abstract class AddressMock {

	public static Address createEntity() {
		return new Address(
			"00000-000",
			"any street",
			"any city",
			"any neighborhood",
			"XX",
			"00"
		);
	}

	public static CreateAddressDto createDto() {
		return new CreateAddressDto(
			"00000-000",
			"any street",
			"any city",
			"any neighborhood",
			"XX",
			"00"
		);
	}

	public static UpdateAddressDto updateDto() {
		return new UpdateAddressDto(
			"11111-111",
			"any street updated",
			"any city updated",
			"any neighborhood updated",
			"YY",
			"11"
		);
	}
}
