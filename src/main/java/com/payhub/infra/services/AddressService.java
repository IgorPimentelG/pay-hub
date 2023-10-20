package com.payhub.infra.services;

import com.payhub.domain.entities.Address;
import com.payhub.infra.dtos.client.CreateAddressDto;
import com.payhub.infra.dtos.client.UpdateAddressDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mappers.AddressMapper;
import com.payhub.infra.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	private final AddressMapper mapper = AddressMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(AddressService.class);

	public Address create(CreateAddressDto data) {
		if (data == null) {
			logger.warn("The address data is null.");
			throw new BadRequestException("The address data cannot be null.");
		}

		var address = mapper.create(data);
		var entity = save(address);

		logger.info("The address with id: {} has been registered.", entity.getId());

		return entity;
	}

	public Address update(UpdateAddressDto data, String id) {
		if (data == null) {
			logger.warn("The address data is null.");
			throw new BadRequestException("The address data cannot be null.");
		}

		var entity = findById(id);
		mapper.update(data, entity);
		save(entity);

		logger.info("The address with id: {} has been updated.", id);

		return entity;
	}

	public Address findById(String id) {
		var entity = repository.findById(id)
			.orElseThrow(() -> {
				logger.warn("The address with id: {} doesn't exist.", id);
				return new NotFoundException("The address with id: " + id + " doesn't exist.");
			});

		logger.info("The address with id: {} was searched and found.", id);

		return entity;
	}

	public Address save(Address address) {
		return repository.save(address);
	}
}
