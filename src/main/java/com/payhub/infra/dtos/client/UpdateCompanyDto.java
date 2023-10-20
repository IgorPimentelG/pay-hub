package com.payhub.infra.dtos.client;

import org.hibernate.validator.constraints.Length;

public record UpdateCompanyDto(
	@Length(max = 100, message = "Corporate name can only have a maximum of 100 characters")
	String corporateName,

	@Length(max = 50, message = "Segment can only have a maximum of 50 characters")
	String segment
) {}
