package com.payhub.infra.dtos.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record CreateClientDto(
	@NotBlank(message = "Email is required.")
	@Email(message = "Email is invalid.")
  String email,

	@NotBlank(message = "Password is required.")
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
		message = "The password must be at least 8 characters long, contain at least one uppercase letter, " +
			"one lowercase letter, one number and one special character."
	)
  String password,

	@NotBlank(message = "CPF is required.")
	@CPF(message = "CPF is invalid. Enter in the format: 000.000.000-00.")
	String cpf,

	@NotBlank(message = "Full name is required.")
	@Length(max = 255, message = "The full name can only have a maximum of 255 characters.")
  String fullName
) {}
