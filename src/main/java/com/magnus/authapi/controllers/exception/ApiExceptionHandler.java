package com.magnus.authapi.controllers.exception;

import com.magnus.authapi.controllers.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public static ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
    ArrayList<String> codes = new ArrayList<>();
    for( ObjectError error : allErrors ) {
      codes.add(error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiExceptionResponse(codes));
  }

  @ExceptionHandler(ApiException.class)
  public static ResponseEntity<BaseResponse> handleApiException(HttpServletRequest req, ApiException ex) {
    if (ex.getStatusCode() != null) {
      ArrayList<String> codes = new ArrayList<>(List.of(ex.getMessage()));
      return ResponseEntity.status(ex.getStatusCode()).body(new ApiExceptionResponse(codes));
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
