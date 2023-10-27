package com.payhub.infra.controllers;

import com.payhub.domain.entities.Transaction;
import com.payhub.infra.controllers.docs.transaction.ApiOperationFind;
import com.payhub.infra.controllers.docs.transaction.ApiOperationFindAll;
import com.payhub.infra.controllers.docs.transaction.ApiOperationRegister;
import com.payhub.infra.dtos.transaction.CardResponse;
import com.payhub.infra.dtos.transaction.TransactionDto;
import com.payhub.infra.dtos.transaction.TransactionResponse;
import com.payhub.infra.services.EncryptorService;
import com.payhub.infra.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@Tag(name = "Transaction", description = "Endpoints for managing transactions")
public class TransactionController {

	@Autowired
	private TransactionService service;

	@Autowired
	private EncryptorService encryptorService;

	@ApiOperationRegister
	@PostMapping("/v1/register")
	public ResponseEntity<TransactionResponse> register(@RequestBody @Valid TransactionDto data) {
		var result = service.create(data);
		return ResponseEntity.ok(formatResponse(result));
	}

	@ApiOperationFind
	@GetMapping("/v1/find/{id}")
	public ResponseEntity<TransactionResponse> find(@PathVariable("id") String id) {
		var result = service.findById(id);
		return ResponseEntity.ok(formatResponse(result));
  }

	@ApiOperationFindAll
	@GetMapping("/v1/find-all")
	public ResponseEntity<List<TransactionResponse>> findAll() {
		var result = service.findAll();
		var formattedResponse = result.stream()
			.map(this::formatResponse)
			.toList();
    return ResponseEntity.ok(formattedResponse);
	}

	private TransactionResponse formatResponse(Transaction transaction) {
		var card = transaction.getCard();
		var number = encryptorService.decrypt(card.getNumber());
		var cardResponse = new CardResponse(
			formatCardNumber(number),
			card.getOwner(),
			card.getValidity(),
			"***"
		);

		return new TransactionResponse(
			transaction.getDescription(),
      transaction.getAmount(),
      transaction.getPaymentMethod().toString().replace("_", " "),
      cardResponse
		);
	}

	private String formatCardNumber(String number) {
		return "**** **** **** " + number.substring(15);
	}
}
