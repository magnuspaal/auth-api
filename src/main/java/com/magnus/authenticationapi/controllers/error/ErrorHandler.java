package com.magnus.authenticationapi.controllers.error;

import com.magnus.authenticationapi.controllers.error.exceptions.EmailAlreadyTakenException;
import com.magnus.authenticationapi.controllers.error.exceptions.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public static ResponseEntity<BaseResponse> handleErrors(Errors errors) {
    ArrayList<String> codes = new ArrayList<>();
    for (ObjectError error: errors.getAllErrors()) {
      codes.add(error.getDefaultMessage());
    }
    return new ResponseEntity<>(new ApiErrorResponse(codes), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmailAlreadyTakenException.class)
  public static ResponseEntity<BaseResponse> handleEmailTaken() {
    ArrayList<String> codes = new ArrayList<>(List.of(ApiErrorCode.EMAIL_ALREADY_TAKEN));
    return new ResponseEntity<>(new ApiErrorResponse(codes), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UsernameAlreadyTakenException.class)
  public static ResponseEntity<BaseResponse> handleUsernameTaken() {
    ArrayList<String> codes = new ArrayList<>(List.of(ApiErrorCode.USERNAME_ALREADY_TAKEN));
    return new ResponseEntity<>(new ApiErrorResponse(codes), HttpStatus.CONFLICT);
  }
}
