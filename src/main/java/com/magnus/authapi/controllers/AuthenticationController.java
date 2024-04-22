package com.magnus.authapi.controllers;

import com.magnus.authapi.auth.*;
import com.magnus.authapi.auth.dto.AuthenticationRequest;
import com.magnus.authapi.auth.dto.RegistrationRequest;
import com.magnus.authapi.controllers.exception.exceptions.InvalidRefreshTokenException;
import com.magnus.authapi.controllers.dto.BaseResponse;
import com.magnus.authapi.controllers.exception.exceptions.EmailAlreadyTakenException;
import com.magnus.authapi.controllers.exception.exceptions.UsernameAlreadyTakenException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  @PostMapping("/register")
  public ResponseEntity<BaseResponse> register(@RequestBody @Valid RegistrationRequest request) throws EmailAlreadyTakenException, UsernameAlreadyTakenException {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<BaseResponse> authenticate(@RequestBody @Valid AuthenticationRequest request, HttpServletResponse response) {
    service.authenticate(request, response);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/refresh")
  public ResponseEntity<BaseResponse> refresh(@CookieValue("refreshToken") String refreshToken, HttpServletResponse response) throws InvalidRefreshTokenException {
    service.refreshToken(refreshToken, response);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/confirm/{token}")
  public ResponseEntity<String> confirm(@PathVariable String token) {
    service.confirmUser(token);
    return ResponseEntity.ok("<h1>Email confirmed<h1>");
  }
}
