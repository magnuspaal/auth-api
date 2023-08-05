package com.magnus.authenticationapi.controllers;

import com.magnus.authenticationapi.auth.*;
import com.magnus.authenticationapi.auth.dto.AuthenticationRequest;
import com.magnus.authenticationapi.auth.dto.RefreshTokenRequest;
import com.magnus.authenticationapi.auth.dto.RegistrationRequest;
import com.magnus.authenticationapi.controllers.error.BaseResponse;
import com.magnus.authenticationapi.controllers.error.exceptions.EmailAlreadyTakenException;
import com.magnus.authenticationapi.controllers.error.exceptions.UsernameAlreadyTakenException;
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
