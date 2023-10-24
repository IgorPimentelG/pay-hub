package com.payhub.infra.services;

import com.payhub.domain.entities.Card;
import com.payhub.infra.dtos.transaction.CardDto;
import com.payhub.infra.errors.BadRequestException;
import com.payhub.infra.mappers.CardMapper;
import com.payhub.infra.repositories.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

	@Autowired
	private CardRepository repository;

	@Autowired
	private EncryptorService encryptorService;

	private final CardMapper mapper = CardMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(CardService.class);

	public Card register(CardDto data) {
		if (data == null) {
			throw new BadRequestException("The card data cannot be null.");
		} else if (!validCardNumber(data.number())) {
      throw new BadRequestException("The card number is invalid.");
    }

		var card = mapper.create(data);
		card.setNumber(encryptorService.encrypt(data.number()));
		card.setCVV(encryptorService.encrypt(data.cvv()));

		logger.info("The card was successfully registered.");

		return repository.save(card);
	}

	private boolean validCardNumber(String number) {
		number = number.replaceAll("\\s", "");

		int sum = 0;
		int multiplier = 2;
		final int lengthToPenultimate = number.length() - 2;
		final int verificationDigit = (int) number.charAt(number.length() - 1) - '0';

		for (int i = lengthToPenultimate; i >= 0; i--) {
			int digit = (int) number.charAt(i) - '0';
			digit *= multiplier;

			if (digit > 9) {
				digit = (digit / 10) + (digit % 10);
			}

			sum += digit;
			multiplier = multiplier == 2 ? 1 : 2;
		}

		return (sum + verificationDigit) % 10 == 0;
	}
}
