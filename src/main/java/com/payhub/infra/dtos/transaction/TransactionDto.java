package com.payhub.infra.dtos.transaction;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TransactionDto(
	@NotBlank(message = "The description is required.")
  @Length(max = 255, message = "The description can only have a maximum of 255 characters.")
	String description,

	@Min(value = 1, message = "Amount is required.")
	BigDecimal amount,

	@NotBlank(message = "The payment method is required.")
	@Pattern(
		regexp = "^(DEBIT_CARD|CREDIT_CARD)$",
		message = "Only methods DEBIT_CARD and CREDIT_CARD are accepted."
	)
	String paymentMethod,

	@Valid
	CardDto card
) {}
