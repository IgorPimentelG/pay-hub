package com.payhub.infra.dtos.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CreateAddressDto(
	@NotBlank(message = "The zip code is required.")
	@Pattern(
		regexp = "^\\d{5}-\\d{3}$",
		message = "The zip code is invalid. Enter in the format: #####-###."
	)
	String zipCode,

	@NotBlank(message = "The street is required.")
	@Length(max = 255, message = "The street can only have a maximum of 255 characters.")
	String street,

	@NotBlank(message = "The city is required.")
	@Length(max = 50, message = "The city can only have a maximum of 50 characters.")
	String city,

	@NotBlank(message = "The neighborhood is required.")
	@Length(max = 100, message = "The neighborhood can only have a maximum of 100 characters.")
	String neighborhood,

	@NotBlank(message = "The state is required.")
	@Pattern(
		regexp = "^[a-zA-Z]{2}$",
		message = "The state is invalid. Enter initials only: ##."
	)
	String state,

	@NotBlank(message = "The number is required.")
	@Length(max = 5, message = "The number can only have a maximum of 5 characters.")
	@Pattern(
		regexp = "^(\\d+|\\d+-[A-Z])$",
		message = "The number is invalid. Enter in the format: 00 or 00-X."
	)
	String number
) {}
