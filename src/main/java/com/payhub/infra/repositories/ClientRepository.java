package com.payhub.infra.repositories;

import com.payhub.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
	Optional<Client> findByEmail(String email);
}
