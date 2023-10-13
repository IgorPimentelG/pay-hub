package com.payhub.infra.dtos.client;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CreateCompanyDto(
	@NotBlank(message = "CNPJ is required.")
	@Pattern(
		regexp = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
		message = "CNPJ is invalid. Enter in the format: 00.000.000/0000-00."
	)
	String cnpj,


	@NotBlank(message = "State registration is required.")
	@Pattern(
		regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}\\.\\d{3})$",
		message = "State registration is invalid. Enter in the format: 000.000.000.000."
	)
	String stateRegistration,

	@NotBlank(message = "Corporate name is required.")
	@Length(max = 100, message = "Corporate name can only have a maximum of 100 characters")
	String corporateName,

	@NotBlank(message = "Segment is required.")
	@Length(max = 50, message = "Segment can only have a maximum of 50 characters")
	String segment,

	@Valid
	CreateAddressDto address
) {}
