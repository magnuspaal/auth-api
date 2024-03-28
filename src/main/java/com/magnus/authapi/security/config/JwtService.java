package com.magnus.authapi.security.config;

import com.magnus.authapi.config.JwtProperties;
import com.magnus.authapi.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final JwtProperties jwtProperties;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && isTokenValid(token);
  }

  public boolean isTokenValid(String token) {
    return extractExpiration(token).after(new Date(System.currentTimeMillis()));
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  public String generateRefreshToken(User user) {
    Date expiresAt = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 31 * 6);
    return generateToken(user, expiresAt);
  }

  public String generateToken(User user, Date expiresAt) {
    return Jwts.builder()
        .setClaims(Map.of(
            "id", user.getId(),
            "first_name", user.getFirstName(),
            "last_name", user.getLastName(),
            "username", user.getHandle(),
            "role", user.getUserRole()))
        .setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(expiresAt)
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(this.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private String getSecretKey() {
    return jwtProperties.getSecretKey();
  }

  public UsernamePasswordAuthenticationToken validateJWT(String jwt) {

    if (this.isTokenValid(jwt)) {
      Claims claims = this.extractAllClaims(jwt);

      User user = new User(
          claims.get("id", Long.class),
          claims.get("first_name", String.class),
          claims.get("last_name", String.class),
          claims.getSubject(),
          claims.get("username", String.class)
      );

      return new UsernamePasswordAuthenticationToken(
          user,
          null,
          Collections.singleton((GrantedAuthority) () -> "USER")
      );
    }
    return null;
  }
}
