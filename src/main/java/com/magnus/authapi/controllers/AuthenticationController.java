package com.magnus.authapi.controllers;

import com.magnus.authapi.auth.*;
import com.magnus.authapi.auth.dto.AuthenticationRequest;
import com.magnus.authapi.auth.dto.RefreshTokenRequest;
import com.magnus.authapi.auth.dto.RegistrationRequest;
import com.magnus.authapi.controllers.dto.BaseResponse;
import com.magnus.authapi.controllers.error.exceptions.EmailAlreadyTakenException;
import com.magnus.authapi.controllers.error.exceptions.UsernameAlreadyTakenException;
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
  public ResponseEntity<BaseResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<BaseResponse> refresh(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(service.refreshToken(request));
  }

  @GetMapping("/confirm/{token}")
  public ResponseEntity<String> confirm(@PathVariable String token) {
    service.confirmUser(token);
    return ResponseEntity.ok("<h1>Email confirmed<h1>");
  }
}
