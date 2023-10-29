package com.payhub.infra.mocks;

import com.payhub.domain.entities.Address;
import com.payhub.infra.dtos.client.CreateAddressDto;
import com.payhub.infra.dtos.client.UpdateAddressDto;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

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

	public static Address createUpdatedEntity() {
		var address = Mockito.mock(Address.class);
		when(address.getId()).thenReturn("any id");
		when(address.getCity()).thenReturn("any city updated");
		when(address.getStreet()).thenReturn("any street updated");
		when(address.getZipCode()).thenReturn("11111-111");
		when(address.getState()).thenReturn("YY");
		when(address.getNumber()).thenReturn("11");
		when(address.getNeighborhood()).thenReturn("any neighborhood updated");
		return address;
	}
}
