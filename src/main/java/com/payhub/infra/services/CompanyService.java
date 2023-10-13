package com.payhub.infra.services;

import com.payhub.domain.entities.Company;
import com.payhub.infra.dtos.client.CreateCompanyDto;
import com.payhub.infra.dtos.client.UpdateCompanyDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mappers.CompanyMapper;
import com.payhub.infra.repositories.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;

	@Autowired
	private AddressService addressService;

	private final CompanyMapper mapper = CompanyMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(CompanyService.class);

	public Company create(CreateCompanyDto data) {
		if (data == null) {
			logger.warn("The company data is null.");
			throw new BadRequestException("It is necessary to provide company details.");
		}

		var address = addressService.create(data.address());
		var company = mapper.create(data);
		company.setAddress(address);
		var entity = repository.save(company);

		logger.info("The company with CNPJ: {} has been registered.", entity.getCnpj());

		return entity;
	}

	public Company update(UpdateCompanyDto data, String id) {
		if (data == null) {
			logger.warn("The company data is null.");
			throw new BadRequestException("It is necessary to provide company details.");
		}

		var entity = findById(id);
		mapper.update(data, entity);
		repository.save(entity);

		logger.info("The company with CNPJ: {} has been updated.", entity.getCnpj());

		return entity;
	}

	public Company findById(String id) {
		var entity = repository.findById(id)
			.orElseThrow(() -> {
				logger.warn("The company with id: {} is not registered.", id);
				return new NotFoundException("The company with id: " + id + " is not registered.");
			});

		logger.info("The company with id: {} was searched and found.", id);

		return entity;
	}

	public Company findByCNPJ(String cnpj) {
		var entity = repository.findByCnpj(cnpj)
			.orElseThrow(() -> {
				logger.warn("The company with CNPJ: {} is not registered.", cnpj);
				return new NotFoundException("The company with CNPJ: " + cnpj + " is not registered.");
			});

		logger.info("The company with CNPJ: {} was searched and found.", cnpj);

		return entity;
	}
}
