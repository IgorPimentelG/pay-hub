package com.payhub.infra.controllers;

import com.payhub.domain.entities.Address;
import com.payhub.infra.controllers.docs.address.ApiOperationFind;
import com.payhub.infra.controllers.docs.address.ApiOperationUpdate;
import com.payhub.infra.dtos.client.UpdateAddressDto;
import com.payhub.infra.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@Tag(name = "Address", description = "Endpoints for managing addresses")
public class AddressController {

	@Autowired
	private AddressService service;

	@ApiOperationUpdate
	@PatchMapping("/v1/update/{id}")
	public ResponseEntity<Address> update(
		@RequestBody @Valid UpdateAddressDto data,
		@PathVariable("id") String id
	) {
		var result = service.update(data, id);
		return ResponseEntity.ok(result);
	}

	@ApiOperationFind
	@GetMapping("/v1/find/{id}")
	public ResponseEntity<Address> find(@PathVariable("id") String id) {
		var result = service.findById(id);
		return ResponseEntity.ok(result);
	}
}
