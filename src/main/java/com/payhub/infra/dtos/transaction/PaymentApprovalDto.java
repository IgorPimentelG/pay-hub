package com.payhub.infra.dtos.transaction;

import com.google.gson.annotations.SerializedName;

public record PaymentApprovalDto(
	String id,

	@SerializedName("account_id")
	String accountId,

	@SerializedName("iso_currency_code")
  String isoCurrencyCode,

	@SerializedName("financial_institution")
  String financialInstitution,

	@SerializedName("account_number")
  String accountNumber,

  String pin,
	boolean accepted
) {}
