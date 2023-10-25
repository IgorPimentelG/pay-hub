package com.payhub.infra.services;

import com.google.gson.Gson;
import com.payhub.domain.entities.Transaction;
import com.payhub.domain.types.PaymentMethod;
import com.payhub.infra.dtos.transaction.PaymentApprovalDto;
import com.payhub.infra.dtos.transaction.TransactionDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.FailTransaction;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mappers.TransactionMapper;
import com.payhub.infra.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private CardService cardService;

	private final TransactionMapper mapper = TransactionMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	public Transaction create(TransactionDto data) {
		if (data == null) {
			throw new BadRequestException("The transaction data cannot be null.");
		}

		var card = cardService.register(data.card());
		var transaction = mapper.create(data);
		transaction.setCard(card);
		transaction.setPaymentMethod(PaymentMethod.valueOf(data.paymentMethod()));

		card.addTransaction(transaction);
		cardService.save(card);

		if (!verifyTransaction(transaction)) {
			throw new FailTransaction();
		}

		logger.info("The transaction {} has been created", transaction.getId());

		return repository.save(transaction);
	}

	public Transaction findById(String id) {
		var entity = repository.findById(id)
			.orElseThrow(() -> new NotFoundException("Transaction not found."));

		logger.info("The transaction {} has been found", entity.getId());

		return entity;
	}

	private boolean verifyTransaction(Transaction transaction) {
		final var gson = new Gson();
		final var restTemplate = new RestTemplate();
		final var PAYMENT_API_URL = "https://run.mocky.io/v3/f11c7eeb-d05e-4176-b4e0-43243f256543";

		HttpEntity<Transaction> request = new HttpEntity<>(transaction);
		ResponseEntity<String> response = restTemplate.postForEntity(PAYMENT_API_URL, request, String.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new FailTransaction();
		}

		PaymentApprovalDto result = gson.fromJson(response.getBody(), PaymentApprovalDto.class);

		return result.accepted();
	}
}


