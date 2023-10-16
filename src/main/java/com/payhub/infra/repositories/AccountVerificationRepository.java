package com.payhub.infra.repositories;

import com.payhub.domain.entities.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerification, String> {
	@Query("SELECT acc FROM AccountVerification acc WHERE acc.client = :clientId AND acc.isExpired = TRUE")
	Optional<AccountVerification> findActiveCode(@Param("clientId") String clientId);
}
