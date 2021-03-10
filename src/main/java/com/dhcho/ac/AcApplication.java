package com.dhcho.ac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcApplication.class, args);
	}

}
