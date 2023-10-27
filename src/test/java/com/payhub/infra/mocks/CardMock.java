package com.payhub.infra.mocks;

import com.payhub.domain.entities.Card;
import com.payhub.infra.dtos.transaction.CardDto;

public abstract class CardMock {

	public static Card createEntity() {
		return new Card(
			"encrypted card number",
			"Any Name",
      "01/30",
      "encrypted card cvv"
		);
	}

	public static CardDto createDto() {
		return new CardDto(
      "5435 0174 2098 5820",
      "Any Name",
      "01/30",
      "000"
    );
	}
}
