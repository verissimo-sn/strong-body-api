package com.aitech.strongBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class StrongBodyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrongBodyApplication.class, args);
	}

}
