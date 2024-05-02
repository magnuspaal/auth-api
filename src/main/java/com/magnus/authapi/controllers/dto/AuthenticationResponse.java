package com.magnus.authapi.controllers.dto;

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
