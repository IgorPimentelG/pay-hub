package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.ClientMock;
import com.payhub.infra.repositories.ClientRepository;
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

public class ClientServiceTests {

	@InjectMocks
	ClientService service;

	@Mock
	ClientRepository repository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should create a client")
	void testCreateClient() {
		var clientEntity = ClientMock.createEntity();
		var clientDto = ClientMock.createDto();

		when(repository.save(any())).thenReturn(clientEntity);

		var result = service.create(clientDto);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getEmail(), clientDto.email());
		assertEquals(result.getPassword(), clientDto.password());
		assertEquals(result.getCpf(), clientDto.cpf());
		assertEquals(result.getFullName(), clientDto.fullName());
	}

	@Test
	@DisplayName("should throws exception when creating a client with data null")
	void testErrorThrownOnCreatingClientWithNullData() {

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "The client data cannot be null.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should update a client")
	void testUpdateClient() {
		var clientEntity = ClientMock.createEntity();
		var clientDto = ClientMock.updateDto();

		when(repository.findById(any())).thenReturn(Optional.of(clientEntity));
		when(repository.save(any())).thenReturn(clientEntity);

		var result = service.update(clientDto, "any id");

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getPassword(), clientDto.password());
	}

	@Test
	@DisplayName("should throws exception when update a client with data null")
	void testErrorThrownOnUpdatingClientWithNullData() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.update(null, "any id");
		});

		String expectedMessage = "The client data cannot be null.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws exception when update a client doesn't exist")
	void testErrorThrownOnUpdatingClientDoesNotExist() {
		var clientUpdateDto = ClientMock.updateDto();

		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.update(clientUpdateDto, "any id");
		});

		String expectedMessage = "The client with id: any id doesn't exist.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should find a client")
	void testFindClient() {
		var clientEntity = ClientMock.createEntity();

		when(repository.findById(any())).thenReturn(Optional.of(clientEntity));

		var result = service.findById("any id");

		verify(repository, times(1)).findById(any());
		assertNotNull(result);
		assertEquals(result.getEmail(), clientEntity.getEmail());
		assertEquals(result.getPassword(), clientEntity.getPassword());
		assertEquals(result.getCpf(), clientEntity.getCpf());
		assertEquals(result.getFullName(), clientEntity.getFullName());
	}

	@Test
	@DisplayName("should throws exception when find client doesn't exist")
	void testErrorThrownOnFindClientDoesNotExist() {
		Exception exception = assertThrows(NotFoundException .class, () -> {
			service.findById("any id");
		});

		String expectedMessage = "The client with id: any id doesn't exist.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should disable a client")
	void testDisableClient() {
		var clientEntity = ClientMock.createEntity();

		when(repository.findById(any())).thenReturn(Optional.of(clientEntity));

		var result = service.disable("any id");

		verify(repository, times(1)).findById(any());
		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertFalse(result.isEnabled());
		assertFalse(result.isAccountNonLocked());
		assertFalse(result.isAccountNonExpired());
		assertFalse(result.isCredentialsNonExpired());
	}

	@Test
	@DisplayName("should throws exception when disable a client doesn't exist")
	void testErrorThrownOnDisableClientDoesNotExist() {
		Exception exception = assertThrows(NotFoundException .class, () -> {
			service.disable("any id");
		});

		String expectedMessage = "The client with id: any id doesn't exist.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
