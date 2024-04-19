package com.magnus.authapi.auth;

import com.magnus.authapi.auth.dto.AuthenticationRequest;
import com.magnus.authapi.auth.dto.AuthenticationResponse;
import com.magnus.authapi.auth.dto.RefreshTokenRequest;
import com.magnus.authapi.auth.dto.RegistrationRequest;
import com.magnus.authapi.controllers.exception.exceptions.InvalidRefreshTokenException;
import com.magnus.authapi.config.ApiProperties;
import com.magnus.authapi.controllers.exception.exceptions.EmailAlreadyTakenException;
import com.magnus.authapi.controllers.exception.exceptions.UsernameAlreadyTakenException;
import com.magnus.authapi.email.EmailSender;
import com.magnus.authapi.token.TokenType;
import com.magnus.authapi.token.UserToken;
import com.magnus.authapi.token.UserTokenRepository;
import com.magnus.authapi.user.User;
import com.magnus.authapi.user.UserRepository;
import com.magnus.authapi.user.UserRole;
import com.magnus.authapi.security.config.JwtService;
import com.magnus.authapi.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserTokenRepository userTokenRepository;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final EmailSender emailSender;
  @Autowired
  private final ApiProperties apiProperties;

  private final Integer tokenExpiry = 1000 * 20; // 5 minutes

  public AuthenticationResponse register(RegistrationRequest request) throws EmailAlreadyTakenException, UsernameAlreadyTakenException {
    String email = request.getEmail().toLowerCase();
    boolean emailExists = userRepository.findByEmail(email).isPresent();
    boolean userExists = userRepository.findByUsername(request.getUsername()).isPresent();
    if (emailExists) throw new EmailAlreadyTakenException();
    if (userExists) throw new UsernameAlreadyTakenException();

    User user = new User(
        request.getFirstName(),
        request.getLastName(),
        request.getUsername(),
        email,
        request.getPassword(),
        UserRole.USER
    );

    return register(user);
  }

  public AuthenticationResponse register(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);

    User savedUser = userRepository.save(user);

    Date expiresAt = new Date(System.currentTimeMillis() + tokenExpiry);
    // Create jwt and refresh tokens
    String jwtToken = jwtService.generateToken(savedUser, expiresAt);
    String refreshToken = this.generateAndSaveRefreshToken(savedUser);

    // Create and send confirmation token
    String confirmationToken = UUID.randomUUID().toString();
    userTokenRepository.save(new UserToken(user, TokenType.CONFIRMATION, confirmationToken));

    String link = String.format("%s/api/v1/auth/confirm/%s", apiProperties.getUrl(), confirmationToken);
    emailSender.send(user.getEmail(), emailSender.buildEmail(user.getFirstName(), link), "Confirm your email address");

    return AuthenticationResponse.builder().token(jwtToken).refreshToken(refreshToken).expiresAt(DateUtils.getDateISO8601GMTString(expiresAt)).build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    String email = request.getEmail().toLowerCase();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            email,
            request.getPassword()
        )
    );

    return generateTokens(email);
  }

  private AuthenticationResponse generateTokens(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    Date expiresAt = new Date(System.currentTimeMillis() + tokenExpiry);
    String jwtToken = jwtService.generateToken(user, expiresAt);
    String refreshToken = this.generateAndSaveRefreshToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .refreshToken(refreshToken)
        .expiresAt(DateUtils.getDateISO8601GMTString(expiresAt))
        .build();
  }

  public synchronized AuthenticationResponse refreshToken(RefreshTokenRequest request) throws InvalidRefreshTokenException {
    String refreshToken = request.getRefreshToken();
    String userEmail = jwtService.extractUsername(refreshToken);
    User user = userRepository.findByEmail(userEmail).orElseThrow();
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

    logger.info( "\u001B[32mrequested new access token\u001B[0m [ " + userEmail + " ]");

    if (jwtService.isTokenValid(refreshToken, userDetails)) {
      UserToken userToken = userTokenRepository.findByUserAndValidAndToken(user, true, refreshToken).orElse(null);

      if (dbRefreshTokenIsValid(userToken)) {
        logger.info("\u001B[32muser token from db\u001B[0m [ " + userEmail + " ], [ " + userToken.getToken() + " ]");

        Date expiresAt = new Date(System.currentTimeMillis() + tokenExpiry);
        invalidateRefreshToken(userToken.getUser(), userToken.getToken());
        String jwtToken = jwtService.generateToken(userToken.getUser(), expiresAt);
        String newRefreshToken = this.generateAndSaveRefreshToken(userToken.getUser());

        logger.info("\u001B[32mreturn refresh token\u001B[0m [ " + userEmail + " ], [ " + newRefreshToken + " ]");
        return AuthenticationResponse.builder().token(jwtToken).refreshToken(newRefreshToken).expiresAt(DateUtils.getDateISO8601GMTString(expiresAt)).build();
      }
    }

    logger.info("\u001B[31minvalid refresh token\u001B[0m [ " + userEmail + " ], [ " + refreshToken + " ]");
    throw new InvalidRefreshTokenException();
  }

  private boolean dbRefreshTokenIsValid(UserToken userToken) {
    boolean notNull = userToken != null;
    if (notNull) {
      boolean valid = userToken.isValid();
      boolean notExpired = userToken.getExpiryDate() == null || LocalDateTime.now().isBefore(userToken.getExpiryDate());
      return valid && notExpired;
    }
    return false;
  }

  public void confirmUser(String token) {
    UserToken userToken = userTokenRepository.findByToken(token)
            .orElse(null);
    if (userToken != null && userToken.isValid() && userToken.getTokenType() == TokenType.CONFIRMATION) {
      userTokenRepository.setValidById(false, userToken.getId());
      userRepository.setEnabled(true, userToken.getUser().getId());
    } else {
      throw new IllegalStateException("confirmation token not found");
    }
  }

  private String generateAndSaveRefreshToken(User user) {
    String refreshToken = jwtService.generateRefreshToken(user);
    deleteRefreshToken(user, refreshToken);
    userTokenRepository.save(new UserToken(user, TokenType.REFRESH_TOKEN, refreshToken));
    return refreshToken;
  }

  private void invalidateRefreshToken(User user, String token) {
    userTokenRepository.findByUserAndValidAndToken(user, true, token).ifPresent((presentToken) -> {
      if (presentToken.getExpiryDate() == null) {
        presentToken.setExpiryDate(LocalDateTime.now().plusMinutes(2));
        userTokenRepository.save(presentToken);
      }
    });
  }

  private void deleteRefreshToken(User user, String token) {
    userTokenRepository.findByUserAndValidAndToken(user, true, token).ifPresent(userTokenRepository::delete);
  }
}
