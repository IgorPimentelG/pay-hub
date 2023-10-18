package com.payhub.main.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(
	title = "Pay Hub",
	description = "API for managing transactions between customer and commerce",
	version = "0.0.1"
))
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(new io.swagger.v3.oas.models.info.Info()
				.title("Pay Hub")
				.version("0.0.1")
				.contact(new Contact()
					.email("dev.igorpimentel@gmail.com")
					.url("https://igorpimentelg.github.io/personal-portfolio/")
				)
				.license(new License()
					.name("Apache 2.0")
					.url("https://www.apache.org/licenses/LICENSE-2.0")
				)
			);
	}
}
