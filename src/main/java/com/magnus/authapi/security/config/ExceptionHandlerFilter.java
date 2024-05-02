package com.magnus.authapi.security.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) {
    try {
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      logger.info("\u001B[31mexpired jwt supplied\u001B[0m " + e.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    } catch (Throwable e) {
      logger.info("\u001B[31munknown exception thrown\u001B[0m " + e.getMessage());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}
