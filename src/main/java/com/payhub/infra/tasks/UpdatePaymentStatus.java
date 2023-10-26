package com.payhub.infra.tasks;

import com.payhub.domain.entities.Payable;
import com.payhub.domain.types.PaymentStatus;
import com.payhub.infra.repositories.PayableRepository;
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
public class UpdatePaymentStatus {

	@Autowired
	private PayableRepository repository;

	private final Logger logger = LoggerFactory.getLogger(UpdatePaymentStatus.class);

	@Scheduled(fixedDelay = 1800000)
	public void doTask() {
		var now = LocalDateTime.now();

		List<Payable> payables = repository.findAllByStatusWaitingFunds();
		payables.forEach(p -> {
			if (p.getPaymentDate().isBefore(now)) {
				p.setStatus(PaymentStatus.PAID);
				repository.save(p);
			}
		});

		var formatter = new SimpleDateFormat("HH:mm:ss");
		logger.info("{}: update payments", formatter.format(new Date()));
	}
}
