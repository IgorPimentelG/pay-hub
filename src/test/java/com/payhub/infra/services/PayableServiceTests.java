package com.payhub.infra.services;

import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.ClientMock;
import com.payhub.infra.mocks.PayableMock;
import com.payhub.infra.mocks.TransactionMock;
import com.payhub.infra.repositories.PayableRepository;
import com.payhub.main.configs.secutiry.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PayableServiceTests {

	@InjectMocks
	PayableService service;

	@Mock
	PayableRepository repository;

	@Mock
	ClientService clientService;

	@Mock
	SecurityContext securityContext;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		when(securityContext.currentUser()).thenReturn(ClientMock.createEntity());
	}

	@Test
	@DisplayName("should register a payable")
	void testRegisterPayable() {
		var transactionEntity = TransactionMock.createEntity();

		when(repository.save(any())).thenReturn(PayableMock.createEntity());

		var result = service.register(transactionEntity);

		assertNotNull(result);
		verify(repository, times(1)).save(any());
	}

	@Test
	@DisplayName("should find all payable")
	void testFindAllPayable() {
		var payableEntity = PayableMock.createEntity();
		var clientEntity = ClientMock.createEntity();
		clientEntity.addPayable(payableEntity);

		when(securityContext.currentUser()).thenReturn(clientEntity);

		var result = service.findAll();

		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getPaymentDate(), payableEntity.getPaymentDate());
		assertEquals(result.get(0).getStatus(), payableEntity.getStatus());
	}

	@Test
	@DisplayName("should find all payable by status")
	void testFindAllPayableByStatus() {
		var payableEntity = PayableMock.createEntity();

		when(repository.findAllByStatus(any(), any())).thenReturn(List.of(payableEntity));

		var result = service.findAllByStatus("PAID");

		assertNotNull(result);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getPaymentDate(), payableEntity.getPaymentDate());
		assertEquals(result.get(0).getStatus(), payableEntity.getStatus());
	}

	@Test
	@DisplayName("should find a payable")
	void testFindPayable() {
		var payableEntity = PayableMock.createEntity();
		when(repository.findById(any(), any())).thenReturn(Optional.of(payableEntity));

		var result = service.findById("any id");

		assertNotNull(result);
		assertEquals(result.getPaymentDate(), payableEntity.getPaymentDate());
		assertEquals(result.getStatus(), payableEntity.getStatus());
	}

	@Test
	@DisplayName("should fail to find payable")
	void testErrorFindPayableNotFound() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.findById("any id");
		});

		String expectedMessage = "Payable not found.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any(), any());
		assertEquals(expectedMessage, resultMessage);
	}
}
