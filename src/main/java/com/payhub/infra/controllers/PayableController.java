package com.payhub.infra.controllers;

import com.payhub.infra.controllers.docs.payable.ApiOperationFindAll;
import com.payhub.infra.controllers.docs.payable.ApiOperationFindById;
import com.payhub.infra.controllers.docs.payable.ApiOperationFindByStatus;
import com.payhub.infra.controllers.helpers.PayableResponseFormatter;
import com.payhub.infra.dtos.transaction.PayableResponse;
import com.payhub.infra.services.PayableService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payable")
@Tag(name = "Payable", description = "Endpoints for find payable")
public class PayableController {

	@Autowired
	private PayableService service;

	@Autowired
		private PayableResponseFormatter responseFormatter;

	@ApiOperationFindAll
	@GetMapping("/v1/find-all")
	public ResponseEntity<List<PayableResponse>> findAll() {
		var result = service.findAll();
		var formattedResult = result.stream()
			.map(responseFormatter::format)
			.toList();
    return ResponseEntity.ok(formattedResult);
	}

	@ApiOperationFindByStatus
	@GetMapping("/v1/find")
	public ResponseEntity<List<PayableResponse>> findByStatus(@RequestParam("status") String status) {
		var result = service.findAllByStatus(status);
		var formattedResult = result.stream()
			.map(responseFormatter::format)
			.toList();
    return ResponseEntity.ok(formattedResult);
	}

	@ApiOperationFindById
	@GetMapping("/v1/find/{id}")
	public ResponseEntity<PayableResponse> findById(@PathVariable("id") String id) {
		var result = service.findById(id);
		return ResponseEntity.ok(responseFormatter.format(result));
	}
}
