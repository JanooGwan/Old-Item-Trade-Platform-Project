package com.example.olditemtradeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OlditemtradeplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlditemtradeplatformApplication.class, args);
	}

}
