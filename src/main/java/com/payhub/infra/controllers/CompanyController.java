package com.payhub.infra.controllers;

import com.payhub.domain.entities.Company;
import com.payhub.infra.controllers.docs.company.ApiOperationFind;
import com.payhub.infra.controllers.docs.company.ApiOperationRegister;
import com.payhub.infra.controllers.docs.company.ApiOperationUpdate;
import com.payhub.infra.dtos.client.CreateCompanyDto;
import com.payhub.infra.dtos.client.UpdateCompanyDto;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mappers.CNPJMapper;
import com.payhub.infra.services.CompanyService;
import com.payhub.main.configs.secutiry.SecurityContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Company", description = "Endpoints for managing company")
public class CompanyController {

	@Autowired
	private CompanyService service;

	@Autowired
	private SecurityContext securityContext;

	@ApiOperationRegister
	@PostMapping("/v1/register")
	public ResponseEntity<Company> register(@RequestBody @Valid CreateCompanyDto data) {
		var result = service.create(data);
		return ResponseEntity.ok(result);
	}

	@ApiOperationUpdate
	@PutMapping("/v1/update")
	public ResponseEntity<Company> update(@RequestBody @Valid UpdateCompanyDto data) {
		var result = service.update(data);
    return ResponseEntity.ok(result);
	}

	@ApiOperationFind
	@GetMapping("/v1/find")
	public ResponseEntity<Company> find(
		@RequestParam(value = "id", required = false) String id,
		@RequestParam(value = "cnpj", required = false) String cnpj
	) {
		Company result = null;

		if (id != null) {
			result = service.findById(id);
		} else if (cnpj != null) {
			result = service.findByCNPJ(CNPJMapper.format(cnpj));
		} else {
			result = securityContext.currentUser().getCompany();
		}

		if (result == null) {
			throw new NotFoundException("No Company was found.");
		}

		return ResponseEntity.ok(result);
	}
}
