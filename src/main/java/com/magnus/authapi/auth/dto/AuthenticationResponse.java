package com.magnus.authapi.auth.dto;

import com.magnus.authapi.controllers.dto.BaseResponse;
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
