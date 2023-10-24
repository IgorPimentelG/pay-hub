package com.payhub.infra.services;

import com.payhub.infra.dtos.transaction.CardDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.mocks.CardMock;
import com.payhub.infra.repositories.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CardServiceTests {

	@InjectMocks
	CardService service;

	@Mock
	CardRepository repository;

	@Mock
	EncryptorService encryptorService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should register a card")
	void testRegisterCard() {
		var cardEntity = CardMock.createEntity();
		var cardDto = CardMock.createDto();

		when(repository.save(any())).thenReturn(cardEntity);
		when(encryptorService.encrypt(cardEntity.getNumber())).thenReturn("encrypted card number");
		when(encryptorService.encrypt(cardEntity.getCVV())).thenReturn("encrypted card cvv");

		var result = service.register(cardDto);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(result.getOwner(), cardEntity.getOwner());
		assertEquals("encrypted card number", result.getNumber());
		assertEquals("encrypted card cvv", result.getCVV());
		assertEquals(result.getValidity(), cardEntity.getValidity());
	}

	@Test
	@DisplayName("should throw exception when register card with data null")
	void testErrorThrownOnRegisterCardWithNullData() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
      service.register(null);
    });

    String expectedMessage = "The card data cannot be null.";
    String resultMessage = exception.getMessage();

    verify(repository, times(0)).save(any());
    assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw exception when register card with invalid number")
	void testErrorThrownOnRegisterCardWithInvalidNumber() {
		var cardDto = new CardDto(
			"5435 0174 2098 5829",
			"Any Name",
			"01/30",
			"000"
		);

		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.register(cardDto);
		});

		String expectedMessage = "The card number is invalid.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
