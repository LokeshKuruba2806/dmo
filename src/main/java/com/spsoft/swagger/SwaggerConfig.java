package com.spsoft.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	protected OpenAPI customOpenAPI() {
		return new OpenAPI()
				/**
				 * Adding security item for bearer authentication
				 */
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				/**
				 * Configuring security scheme for JWT
				 */
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
								.in(SecurityScheme.In.HEADER).name("Authorization")));
	}
}