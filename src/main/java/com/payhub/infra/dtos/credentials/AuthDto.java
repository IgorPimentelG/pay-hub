package com.payhub.infra.dtos.credentials;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record AuthDto(
  @JsonProperty("access_token")
  String accessToken,

  @JsonProperty("refresh_token")
  String refreshToken,

  @JsonProperty("access_token_expiration")
  Date accessTokenExpiration,

  @JsonProperty("refresh_token_expiration")
  Date refreshTokenExpiration
) {}
