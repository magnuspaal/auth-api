package com.magnus.authapi.auth.dto;

import com.magnus.authapi.controllers.exception.ApiExceptionCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @NotNull(message = ApiExceptionCode.EMAIL_NULL)
  @Email(message = ApiExceptionCode.INVALID_EMAIL)
  private String email;
  String password;
}
