package com.magnus.authenticationapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) {
    return ResponseEntity.ok(service.refreshToken(request));
  }

  @GetMapping("/confirm/{token}")
  public ResponseEntity<String> confirm(@PathVariable String token) {
    service.confirmUser(token);
    return ResponseEntity.ok("<h1>Email confirmed<h1>");
  }
}
