package com.payhub.infra.services;

import com.payhub.domain.entities.Payable;
import com.payhub.domain.entities.Transaction;
import com.payhub.domain.types.PaymentMethod;
import com.payhub.domain.types.PaymentStatus;
import com.payhub.infra.dtos.transaction.ReportResponse;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.repositories.PayableRepository;
import com.payhub.main.configs.secutiry.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PayableService {

	@Autowired
	private PayableRepository repository;

	@Autowired
	private ClientService clientService;

	@Autowired
	private SecurityContext securityContext;

	private final Logger logger = LoggerFactory.getLogger(PayableService.class);

	public Payable register(Transaction transaction) {
		var client = securityContext.currentUser();
		var payable = new Payable(
			transaction,
			client,
			getPaymentDate(transaction.getPaymentMethod()),
			getPaymentStatus(transaction.getPaymentMethod())
		);

		client.addPayable(payable);
		clientService.save(client);

		logger.info("The payable {} has been created", payable.getId());

		return repository.save(payable);
	}

	public Payable findById(String id) {
		var entity = repository.findById(id, securityContext.currentUser().getId())
      .orElseThrow(() -> new NotFoundException("Payable not found."));

    logger.info("The payable {} has been found", entity.getId());

    return entity;
	}

	public List<Payable> findAll() {
		logger.info("All payable has been listed");
    return securityContext.currentUser().getPayables();
  }

	public List<Payable> findAllByStatus(String status) {
		logger.info("All payable with status {} has been listed", status);
    return repository.findAllByStatus(PaymentStatus.valueOf(status), securityContext.currentUser().getId());
	}

	public ReportResponse report() {
	  NumberFormat formatter = NumberFormat.getCurrencyInstance();
	  List<Payable> payables = findAll();

	  BigDecimal totalReceivable = BigDecimal.ZERO;
	  BigDecimal totalReceived = BigDecimal.ZERO;

	  for(Payable payable : payables) {
		var amount = payable.getTransaction().getAmount();

		if (payable.getStatus() == PaymentStatus.PAID) {
			totalReceived = totalReceived.add(amount);
		} else {
			totalReceivable = totalReceivable.add(amount);
		}
	  }

	  logger.info("Report has been created.");

	  return new ReportResponse(
		formatter.format(totalReceivable),
		formatter.format(totalReceived),
		payables.size()
	  );
	}

	private LocalDateTime getPaymentDate(PaymentMethod method) {
		return switch (method) {
			case DEBIT_CARD -> LocalDateTime.now();
      case CREDIT_CARD ->  LocalDateTime.now().plusDays(30);
    };
	}

	private PaymentStatus getPaymentStatus(PaymentMethod method) {
		return switch (method) {
			case DEBIT_CARD -> PaymentStatus.PAID;
			case CREDIT_CARD -> PaymentStatus.WAITING_FUNDS;
		};
	}
}
