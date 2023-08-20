package com.magnus.authapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.mail")
@Getter
@Setter
public class MailProperties {
  private String host;
  private String port;
  private String from;
  private String password;
}