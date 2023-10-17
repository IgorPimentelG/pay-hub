package com.payhub.infra.tasks;

import com.payhub.domain.entities.AccountVerification;
import com.payhub.infra.repositories.AccountVerificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class RevalidateVerificationCode {

	@Autowired
	private AccountVerificationRepository repository;

	private final Logger logger = LoggerFactory.getLogger(RevalidateVerificationCode.class);

	@Scheduled(fixedDelay = 1800000)
	public void doTask() {
		var now = LocalDateTime.now();

		List<AccountVerification> verifications = repository.findAllActiveCodes();
		verifications.forEach(v -> {
			if (v.getExpiration().isBefore(now)) {
				v.setExpired(true);
				repository.save(v);
			}
		});

		var formatter = new SimpleDateFormat("HH:mm:ss");
		logger.info("{}: revalidate verification code", formatter.format(new Date()));
	}
}
