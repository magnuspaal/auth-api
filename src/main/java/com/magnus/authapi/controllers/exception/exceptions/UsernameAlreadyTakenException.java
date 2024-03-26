package com.magnus.authapi.controllers.exception.exceptions;

import com.magnus.authapi.controllers.exception.ApiException;
import com.magnus.authapi.controllers.exception.ApiExceptionCode;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyTakenException extends ApiException {
  public UsernameAlreadyTakenException() {
    super(ApiExceptionCode.USERNAME_ALREADY_TAKEN, HttpStatus.CONFLICT);
  }
}
