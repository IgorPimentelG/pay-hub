package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mocks.*;
import com.payhub.infra.repositories.TransactionRepository;
import com.payhub.main.configs.secutiry.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class TransactionServiceTests {

	@InjectMocks
	TransactionService service;

	@Mock
	TransactionRepository repository;

	@Mock
	CardService cardService;

	@Mock
	PayableService payableService;

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
	@DisplayName("should create a transaction")
	void testCreateTransaction() {
		var cardEntity = CardMock.createEntity();
		var transactionEntity = TransactionMock.createEntity();
		var transactionDto = TransactionMock.createDto();

		when(repository.save(any())).thenReturn(transactionEntity);
		when(cardService.save(any())).thenReturn(cardEntity);
		when(cardService.register(any())).thenReturn(cardEntity);

		var restTemplate = new RestTemplate();
		var mockServer = MockRestServiceServer.createServer(restTemplate);

		mockServer.expect(
			requestTo("https://run.mocky.io/v3/f11c7eeb-d05e-4176-b4e0-43243f256543"))
			.andRespond(withSuccess("{\"accepted\": true}", MediaType.APPLICATION_JSON));

		ReflectionTestUtils.setField(service, "restTemplate", restTemplate);

		var result = service.create(transactionDto);

		assertNotNull(result);
		assertEquals(result.getDescription(), transactionEntity.getDescription());
		assertEquals(result.getAmount(), transactionEntity.getAmount());
		assertEquals(result.getPaymentMethod(), transactionEntity.getPaymentMethod());
	}

	@Test
	@DisplayName("should fail on create transaction")
	void testErrorOnCreateTransaction() {
		when(securityContext.currentUser()).thenReturn(ClientMock.createEntity());

		Exception exception = assertThrows(BadRequestException.class, () -> {
      service.create(TransactionMock.createDto());
    });

    String expectedMessage = "You don't have a registered company.";
    String resultMessage = exception.getMessage();

    verify(repository, times(0)).save(any());
    assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should fail on create transaction when the client doesn't have a registered company")
	void testErrorOnCreateTransactionWhenClientDoesNotHaveCompany() {
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

	@Test
	@DisplayName("should find all transactions")
	void testFindAllTransactions() {
		var transactionEntity = TransactionMock.createEntity();
		var payableEntity = PayableMock.createEntity(transactionEntity);

    when(payableService.findAll()).thenReturn(List.of(payableEntity));

    var result = service.findAll();

    assertNotNull(result);
    assertEquals(result.get(0).getDescription(), transactionEntity.getDescription());
    assertEquals(result.get(0).getAmount(), transactionEntity.getAmount());
    assertEquals(result.get(0).getPaymentMethod(), transactionEntity.getPaymentMethod());
	}
}
