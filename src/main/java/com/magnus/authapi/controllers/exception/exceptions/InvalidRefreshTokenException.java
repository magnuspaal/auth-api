package com.magnus.authapi.controllers.exception.exceptions;

import com.magnus.authapi.controllers.exception.ApiException;
import com.magnus.authapi.controllers.exception.ApiExceptionCode;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends ApiException {
  public InvalidRefreshTokenException() {
    super(ApiExceptionCode.INVALID_REFRESH_TOKEN, HttpStatus.UNAUTHORIZED);
  }
}
