package com.payhub.infra.dtos.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CardDto(
	@NotBlank(message = "The number card is required.")
	@Pattern(
		regexp = "^(\\d{4}\\s)(\\d{4}\\s)(\\d{4}\\s)(\\d{4})$",
		message = "The number card is invalid. Enter in the format: 0000 0000 0000 0000."
	)
	String number,

	@NotBlank(message = "The name on the card is required.")
	@Length(min = 4, message = "The name on the card is required.")
	String owner,

	@NotBlank(message = "The validity card is required.")
	@Pattern(
		regexp = "^(\\d{2})/(\\d{2})$",
		message = "The validity card is invalid. Enter in the format: MM/YY."
	)
	String validity,

	@NotBlank(message = "The cvv card is required.")
	@Pattern(
		regexp = "^(\\d{3})$",
		message = "The cvv is invalid. Enter in the format: 000"
	)
  String cvv
) {}
