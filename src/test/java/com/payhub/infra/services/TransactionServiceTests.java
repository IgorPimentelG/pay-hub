package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.CardMock;
import com.payhub.infra.mocks.TransactionMock;
import com.payhub.infra.repositories.TransactionRepository;
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

public class TransactionServiceTests {

	@InjectMocks
	TransactionService service;

	@Mock
	TransactionRepository repository;

	@Mock
	CardService cardService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should create a transaction")
	void testCreateTransaction() {
		var cardEntity = CardMock.createEntity();
		var transactionEntity = TransactionMock.createEntity();
		var transactionDto = TransactionMock.createDto();

		when(repository.save(any())).thenReturn(transactionEntity);
		when(cardService.save(any())).thenReturn(cardEntity);
		when(cardService.register(any())).thenReturn(cardEntity);

		var result = service.create(transactionDto);

		assertNotNull(result);
		assertEquals(result.getDescription(), transactionEntity.getDescription());
		assertEquals(result.getAmount(), transactionEntity.getAmount());
		assertEquals(result.getPaymentMethod(), transactionEntity.getPaymentMethod());
	}

	@Test
	@DisplayName("should fail on create transaction")
	void testErrorOnCreateTransaction() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
      service.create(null);
    });

    String expectedMessage = "The transaction data cannot be null.";
    String resultMessage = exception.getMessage();

    verify(repository, times(0)).save(any());
    assertEquals(expectedMessage, resultMessage);
	}

	@Test
  @DisplayName("should find a transaction")
  void testFindTransaction() {
		var transactionEntity = TransactionMock.createEntity();
		when(repository.findById(any())).thenReturn(Optional.of(transactionEntity));

		var result = service.findById("any id");

		assertNotNull(result);
		assertEquals(result.getDescription(), transactionEntity.getDescription());
		assertEquals(result.getAmount(), transactionEntity.getAmount());
		assertEquals(result.getPaymentMethod(), transactionEntity.getPaymentMethod());
	}

	@Test
	@DisplayName("should fail to find a transaction")
  void testFindTransactionNotFound() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.findById("any id");
		});

		String expectedMessage = "Transaction not found.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
		verify(repository, times(1)).findById(any());
	}
}
