package com.payhub.infra.repositories;

import com.payhub.domain.entities.Payable;
import com.payhub.domain.types.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayableRepository extends JpaRepository<Payable, String> {
	@Query("SELECT p FROM Payable p WHERE p.id = :id AND p.client = :clientId")
	Optional<Payable> findById(String id, String clientId);

	@Query("SELECT p FROM Payable p WHERE p.status = 'WAITING_FUNDS'")
	List<Payable> findAllByStatusWaitingFunds();

	@Query("SELECT p FROM Payable p WHERE p.status = :status AND p.client = :clientId")
	List<Payable> findAllByStatus(PaymentStatus status, String clientId);
}
