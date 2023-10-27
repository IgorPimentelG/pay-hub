package com.payhub.infra.repositories;

import com.payhub.domain.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
	Optional<Card> findByNumber(String number);
}
