package com.payhub.infra.services;

import com.payhub.infra.errors.FailVerification;
import com.payhub.infra.mocks.AccountVerificationMock;
import com.payhub.infra.mocks.ClientMock;
import com.payhub.infra.repositories.AccountVerificationRepository;
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

public class AccountVerificationServiceTests {

	@InjectMocks
	AccountVerificationService service;

	@Mock
	AccountVerificationRepository repository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should generate account verification")
	void testGenerateCode() {
		var client = ClientMock.createEntity();

		var result = service.generateCode(client);

		assertNotNull(result);
		assertNotNull(result.getCode());
		assertNotNull(result.getExpiration());
		assertFalse(result.isExpired());
		assertFalse(result.isVerified());
		verify(repository, times(1)).save(any());
	}

	@Test
	@DisplayName("should verify code")
	void testVerifyCode() {
		var accountVerification = AccountVerificationMock.createEntity();

		when(repository.findActiveCode(any())).thenReturn(Optional.of(accountVerification));
		when(repository.save(any())).thenReturn(accountVerification);

		var result = service.verifyCode("any code", "any id");

		assertNotNull(result);
		assertTrue(result.isVerified());
		assertTrue(result.isExpired());
		verify(repository, times(1)).findActiveCode(any());
		verify(repository, times(1)).save(any());
	}

	@Test
	@DisplayName("should throws exception when fail verification")
	void testErrorThrowOnVerifyCode() {
		Exception exception = assertThrows(FailVerification.class, () -> {
			service.verifyCode("any code", "any id");
		});

		String expectedMessage = "Account verification failed.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
		verify(repository, times(1)).findActiveCode(any());
		verify(repository, times(0)).save(any());
	}
}
