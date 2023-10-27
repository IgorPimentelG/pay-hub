package com.payhub.infra.dtos.transaction;

public record CardResponse(
	String number,
	String owner,
	String validity,
	String cvv
) {}
