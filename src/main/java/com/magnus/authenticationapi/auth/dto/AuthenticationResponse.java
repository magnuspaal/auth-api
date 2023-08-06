package com.magnus.authenticationapi.auth.dto;

import com.magnus.authenticationapi.controllers.dto.BaseResponse;
import com.magnus.authenticationapi.controllers.error.ApiErrorResponse;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends BaseResponse {
  private String token;
  private String refreshToken;
  private String expiresAt;
}
