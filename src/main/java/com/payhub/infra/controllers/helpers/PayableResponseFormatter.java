package com.payhub.infra.controllers.helpers;

import com.payhub.domain.entities.Payable;
import com.payhub.infra.dtos.transaction.PayableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayableResponseFormatter {

	@Autowired
	private TransactionResponseFormatter transactionResponseFormatter;

	public PayableResponse format(Payable payable) {
		var transaction = transactionResponseFormatter.format(payable.getTransaction());
		return new PayableResponse(
			payable.getId(),
			payable.getPaymentDate(),
      payable.getStatus().toString().replace("_", " "),
      transaction
		);
	}
}
