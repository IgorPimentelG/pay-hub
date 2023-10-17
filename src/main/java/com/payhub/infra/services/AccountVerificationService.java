package com.payhub.infra.services;

import com.payhub.domain.entities.AccountVerification;
import com.payhub.domain.entities.Client;
import com.payhub.infra.errors.ExpiredVerification;
import com.payhub.infra.errors.FailVerification;
import com.payhub.infra.repositories.AccountVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AccountVerificationService {

		@Autowired
		private AccountVerificationRepository repository;

		public AccountVerification generateCode(Client client) {
			var random = new Random();
			var code = new StringBuilder();
			var expiration = getExpiration();

			while(code.length() < 6) {
				var number = random.nextInt(9);
				code.append(number);
			}

			var accountVerification = new AccountVerification(
				code.toString(),
				client,
				expiration
			);

			repository.save(accountVerification);

			return accountVerification;
		}

		public void verifyCode(String code, String clientId) {
			var now = LocalDateTime.now();
			var entity = repository.findAllCodesByClient(clientId)
				.orElseThrow(FailVerification::new);

			if (entity.isExpired() || entity.getExpiration().isBefore(now)) {
				throw new ExpiredVerification();
			} else if (!entity.getCode().equals(code) || entity.isVerified()) {
				throw new FailVerification();
			}

			entity.setVerified(true);
			entity.setExpired(true);
			repository.save(entity);
		}

		private LocalDateTime getExpiration() {
			var now = LocalDateTime.now();
			return now.plusHours(1);
		}
}
