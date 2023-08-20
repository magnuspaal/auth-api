package com.magnus.authapi;

import com.magnus.authapi.config.ApiProperties;
import com.magnus.authapi.config.JwtProperties;
import com.magnus.authapi.config.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, ApiProperties.class, MailProperties.class})
public class AuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApiApplication.class, args);
	}

}
