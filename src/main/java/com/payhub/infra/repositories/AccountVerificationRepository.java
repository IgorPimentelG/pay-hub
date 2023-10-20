package com.payhub.infra.repositories;

import com.payhub.domain.entities.AccountVerification;
import com.payhub.domain.types.VerificationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerification, String> {
	@Query("SELECT acv FROM AccountVerification acv WHERE acv.client.id = :clientId AND acv.method = :method")
	List<AccountVerification> findAllCodesByClient(
		@Param("clientId") String clientId,
		@Param("method") VerificationMethod method
	);

	@Query("SELECT acv FROM AccountVerification acv WHERE acv.isExpired = FALSE")
	List<AccountVerification> findAllActiveCodes();
}
