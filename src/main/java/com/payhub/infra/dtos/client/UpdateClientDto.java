package com.payhub.infra.dtos.client;

import jakarta.validation.constraints.Pattern;

public record UpdateClientDto(
	@Pattern(
		regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n",
		message = "The password must be at least 8 characters long, contain at least one uppercase letter, " +
			"one lowercase letter, one number and one special character."
	)
	String password
) {}
