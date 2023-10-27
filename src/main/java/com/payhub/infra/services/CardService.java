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

import java.time.LocalDate;
import java.util.Optional;

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
    } else if (!validCardDate(data.validity())) {
			throw new BadRequestException("The card validity is invalid.");
		}

		var cardRegistered = findByNumber(encryptorService.encrypt(data.number()));

		if (cardRegistered.isPresent()) {
			return cardRegistered.get();
		}

		var card = mapper.create(data);
		card.setNumber(encryptorService.encrypt(data.number()));
		card.setCVV(encryptorService.encrypt(data.cvv()));

		logger.info("The card was successfully registered.");

		return save(card);
	}

	private Optional<Card> findByNumber(String number) {
		return repository.findByNumber(number);
	}

	public Card save(Card card) {
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

	private boolean validCardDate(String date) {
		var now = LocalDate.now();
		var separateDate = date.split("/");
		var cardMonth = Integer.parseInt(separateDate[0]);
		var cardYear = Integer.parseInt(separateDate[1]);
		var currentMonth = now.getMonth().getValue();
		var currentYear = Integer.parseInt(String.valueOf(now.getYear()).substring(2));


		if ((cardYear < currentYear) || (cardMonth < 1 || cardMonth > 12)) {
			return false;
		} else {
			return !(cardYear == currentYear && cardMonth > currentMonth);
		}
	}
}
