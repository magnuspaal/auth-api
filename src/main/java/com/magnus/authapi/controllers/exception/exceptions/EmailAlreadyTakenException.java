package com.magnus.authapi.controllers.exception.exceptions;

import com.magnus.authapi.controllers.exception.ApiException;
import com.magnus.authapi.controllers.exception.ApiExceptionCode;
import org.springframework.http.HttpStatus;

public class EmailAlreadyTakenException extends ApiException {
  public EmailAlreadyTakenException() {
    super(ApiExceptionCode.EMAIL_ALREADY_TAKEN, HttpStatus.CONFLICT);
  }
}
