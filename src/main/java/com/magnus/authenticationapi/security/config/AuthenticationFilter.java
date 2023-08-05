package com.magnus.authenticationapi.security.config;

import com.magnus.authenticationapi.serverauthentication.ServerAuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
@Service
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  @SneakyThrows
  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      Authentication authentication = ServerAuthenticationService.getAuthentication((HttpServletRequest) request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exp) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = response.getWriter();
      writer.print(exp.getMessage());
      writer.flush();
      writer.close();
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
      throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/api/v1/auth/confirm/");
  }
}
