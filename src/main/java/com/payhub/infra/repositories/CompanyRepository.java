package com.payhub.infra.repositories;

import com.payhub.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
	Optional<Company> findByCnpj(String cnpj);
}
