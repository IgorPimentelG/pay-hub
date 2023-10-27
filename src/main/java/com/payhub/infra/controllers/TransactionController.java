package com.payhub.infra.controllers;

import com.payhub.infra.controllers.docs.transaction.ApiOperationFind;
import com.payhub.infra.controllers.docs.transaction.ApiOperationFindAll;
import com.payhub.infra.controllers.docs.transaction.ApiOperationRegister;
import com.payhub.infra.controllers.helpers.TransactionResponseFormatter;
import com.payhub.infra.dtos.transaction.TransactionDto;
import com.payhub.infra.dtos.transaction.TransactionResponse;
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
	private TransactionResponseFormatter responseFormatter;

	@ApiOperationRegister
	@PostMapping("/v1/register")
	public ResponseEntity<TransactionResponse> register(@RequestBody @Valid TransactionDto data) {
		var result = service.create(data);
		return ResponseEntity.ok(responseFormatter.format(result));
	}

	@ApiOperationFind
	@GetMapping("/v1/find/{id}")
	public ResponseEntity<TransactionResponse> find(@PathVariable("id") String id) {
		var result = service.findById(id);
		return ResponseEntity.ok(responseFormatter.format(result));
  }

	@ApiOperationFindAll
	@GetMapping("/v1/find-all")
	public ResponseEntity<List<TransactionResponse>> findAll() {
		var result = service.findAll();
		var formattedResponse = result.stream()
			.map(responseFormatter::format)
			.toList();
    return ResponseEntity.ok(formattedResponse);
	}
}
