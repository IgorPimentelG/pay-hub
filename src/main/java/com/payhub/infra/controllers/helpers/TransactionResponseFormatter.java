package com.payhub.infra.controllers.helpers;

import com.payhub.domain.entities.Transaction;
import com.payhub.infra.dtos.transaction.CardResponse;
import com.payhub.infra.dtos.transaction.TransactionResponse;
import com.payhub.infra.services.EncryptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionResponseFormatter {

	@Autowired
	private EncryptorService encryptorService;

	public TransactionResponse format(Transaction transaction) {
		var card = transaction.getCard();
		var number = encryptorService.decrypt(card.getNumber());
		var cardResponse = new CardResponse(
			formatCardNumber(number),
			card.getOwner(),
			card.getValidity(),
			"***"
		);

		return new TransactionResponse(
			transaction.getDescription(),
			transaction.getAmount(),
			transaction.getPaymentMethod().toString().replace("_", " "),
			cardResponse
		);
	}

	private String formatCardNumber(String number) {
		return "**** **** **** " + number.substring(15);
	}
}
