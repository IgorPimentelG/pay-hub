package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.AddressMock;
import com.payhub.infra.mocks.ClientMock;
import com.payhub.infra.mocks.CompanyMock;
import com.payhub.infra.repositories.AddressRepository;
import com.payhub.main.configs.secutiry.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddressServiceTests {

	@InjectMocks
	AddressService service;

	@Mock
	AddressRepository repository;

	@Mock
	SecurityContext securityContext;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		var client = ClientMock.createEntity();
		client.setCompany(CompanyMock.createEntity());
		when(securityContext.currentUser()).thenReturn(client);
	}

	@Test
	@DisplayName("should create an address")
	void testCreateAddress() {
		var addressEntity = AddressMock.createEntity();
		var addressDto = AddressMock.createDto();

		when(repository.save(any())).thenReturn(addressEntity);

		var result = service.create(addressDto);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getZipCode(), addressDto.zipCode());
		assertEquals(result.getState(), addressDto.state());
		assertEquals(result.getCity(), addressDto.city());
		assertEquals(result.getNeighborhood(), addressDto.neighborhood());
		assertEquals(result.getStreet(), addressDto.street());
		assertEquals(result.getNumber(), addressDto.number());
	}

	@Test
	@DisplayName("should throws exception when creating address with data null")
	void testErrorThrownOnCreatingAddressWithNullData() {

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "The address data cannot be null.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should update an address")
	void testUpdateAddress() {
		var addressEntity = AddressMock.createUpdatedEntity();
		var addressDto = AddressMock.updateDto();
		var client = ClientMock.createEntity();
		var company = CompanyMock.createEntity();
		company.setAddress(addressEntity);
		client.setCompany(company);

		var id = "any id";

		when(addressEntity.getId()).thenReturn(id);
		when(securityContext.currentUser()).thenReturn(client);
		when(repository.findById(any())).thenReturn(Optional.of(addressEntity));
		when(repository.save(any())).thenReturn(addressEntity);

		var result = service.update(addressDto, id);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getZipCode(), addressDto.zipCode());
		assertEquals(result.getState(), addressDto.state());
		assertEquals(result.getCity(), addressDto.city());
		assertEquals(result.getNeighborhood(), addressDto.neighborhood());
		assertEquals(result.getStreet(), addressDto.street());
		assertEquals(result.getNumber(), addressDto.number());
	}

	@Test
	@DisplayName("should throws exception when update address with data null")
	void testErrorThrownOnUpdatingAddressWithNullData() {

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.update(null, "any id");
		});

		String expectedMessage = "The address data cannot be null.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws exception when update address doesn't exist")
	void testErrorThrownOnUpdatingAddressDoesNotExist() {

		var addressUpdateDto = AddressMock.updateDto();

		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.update(addressUpdateDto, "any id");
		});

		String expectedMessage = "The address with id: any id doesn't exist.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should find an address")
	void testFindAnAddress() {
		var addressEntity = AddressMock.createEntity();

		when(repository.findById(any())).thenReturn(Optional.of(addressEntity));

		var result = service.findById("any id");

		verify(repository, times(1)).findById(any());
		assertNotNull(result);
		assertEquals(result.getZipCode(), addressEntity.getZipCode());
		assertEquals(result.getState(), addressEntity.getState());
		assertEquals(result.getCity(), addressEntity.getCity());
		assertEquals(result.getNeighborhood(), addressEntity.getNeighborhood());
		assertEquals(result.getStreet(), addressEntity.getStreet());
		assertEquals(result.getNumber(), addressEntity.getNumber());
	}

	@Test
	@DisplayName("should throws exception when find address doesn't exist")
	void testErrorThrownOnFindAddressDoesNotExist() {

		Exception exception = assertThrows(NotFoundException .class, () -> {
			service.findById("any id");
		});

		String expectedMessage = "The address with id: any id doesn't exist.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
