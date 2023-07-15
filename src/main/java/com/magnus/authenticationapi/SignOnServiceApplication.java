package com.magnus.authenticationapi;

import com.magnus.authenticationapi.config.ApiProperties;
import com.magnus.authenticationapi.config.JwtProperties;
import com.magnus.authenticationapi.config.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, ApiProperties.class, MailProperties.class})
public class SignOnServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SignOnServiceApplication.class, args);
	}

}
