package com.payhub.infra.services;

import com.payhub.domain.entities.Client;
import com.payhub.infra.dtos.client.CreateClientDto;
import com.payhub.infra.dtos.client.UpdateClientDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.errors.NotFoundException;
import com.payhub.infra.mappers.ClientMapper;
import com.payhub.infra.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	private final ClientMapper mapper = ClientMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(ClientService.class);

	public Client create(CreateClientDto data) {
		if (data == null) {
			logger.warn("Client data is null.");
			throw new BadRequestException("The client data cannot be null.");
		}

		var client = mapper.create(data);
		client.setPassword(encoder.encode(data.password()));
		var entity = repository.save(client);

		logger.info("The client with CPF: {} was registered.", entity.getCpf());

		return entity;
	}

	public Client update(UpdateClientDto data, String id) {
		if (data == null) {
			logger.warn("Client data is null.");
			throw new BadRequestException("The client data cannot be null.");
		}

		var entity = findById(id);
		mapper.update(data, entity);
		repository.save(entity);

		return entity;
	}

	public Client findById(String id) {
		var entity = repository.findById(id)
			.orElseThrow(() -> {
				logger.warn("The client with id: {} doesn't exist.", id);
				return new NotFoundException("The client with id: " + id + " doesn't exist.");
			});

		logger.info("The client with id: {} was searched and found.", id);

		return entity;
	}

	public Client findByEmail(String email) {
		var entity = repository.findByEmail(email)
			.orElseThrow(() -> {
				logger.warn("The client with email: {} doesn't exist.", email);
				return new NotFoundException("The client with email: " + email + " doesn't exist.");
			});

		logger.info("The client with email: {} was searched and found.", email);

		return entity;
	}

	public Client active(String id) {
		var entity = findById(id);

		entity.setEnabled(true);
		entity.setAccountNonLocked(true);
		entity.setAccountNonExpired(true);
		entity.setCredentialsNonExpired(true);
		repository.save(entity);

		logger.info("The client with id: {} has been activated.", id);

		return entity;
	}

	public Client disable(String id) {
		var entity = findById(id);

		entity.setEnabled(false);
		entity.setAccountNonLocked(false);
		entity.setAccountNonExpired(false);
		entity.setCredentialsNonExpired(false);
		repository.save(entity);

		logger.info("The client with id: {} was disabled.", id);

		return entity;
	}
}
