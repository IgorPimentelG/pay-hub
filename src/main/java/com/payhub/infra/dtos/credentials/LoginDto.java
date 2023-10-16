package com.payhub.infra.dtos.credentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDto(
  @NotBlank(message = "Email is required.")
  @Email(message = "Email is invalid.")
  String email,

  @NotBlank(message = "Password is required.")
  String password
) {}
