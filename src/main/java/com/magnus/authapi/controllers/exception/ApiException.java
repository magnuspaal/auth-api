package com.magnus.authapi.controllers.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends Throwable {
  private final HttpStatus statusCode;

  public ApiException(String detailMessage, HttpStatus statusCode) {
    super(detailMessage);
    this.statusCode = statusCode;
  }
}