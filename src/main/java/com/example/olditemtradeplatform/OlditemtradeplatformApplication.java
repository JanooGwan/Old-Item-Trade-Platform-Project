package com.example.olditemtradeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableJpaAuditing
@SpringBootApplication
public class OlditemtradeplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlditemtradeplatformApplication.class, args);
	}

}
