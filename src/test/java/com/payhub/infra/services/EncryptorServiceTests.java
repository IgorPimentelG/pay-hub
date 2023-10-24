package com.payhub.infra.services;

import com.payhub.infra.errors.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptorServiceTests {

	@InjectMocks
	EncryptorService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should encrypt data")
	void testRegisterCard() {
		var data = "any data";
		var result = service.encrypt(data);

		assertNotNull(result);
		assertNotEquals(result, data);
	}

	@Test
	@DisplayName("should throw exception when encrypt fail")
	void testThrowsExceptionWhenEncryptFails() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.encrypt(null);
		});

		String expectedMessage = "Failed to encrypt data.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
  @DisplayName("should decrypt data")
  void testDecrypt() {
		var data = "any data";
		var encrypted = service.encrypt(data);
		var result = service.decrypt(encrypted);

		assertNotNull(encrypted);
		assertNotNull(result);
		assertNotEquals(encrypted, data);
		assertEquals(result, data);
	}

	@Test
	@DisplayName("should throw exception when decrypt fail")
	void testThrowsExceptionWhenDecryptFails() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.decrypt(null);
		});

		String expectedMessage = "Failed to decrypt data.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}
