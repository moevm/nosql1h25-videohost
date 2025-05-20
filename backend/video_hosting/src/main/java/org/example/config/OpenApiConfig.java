package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI expenseAPI() {

                SecurityScheme securityScheme = new SecurityScheme()
                        .name("bearerAuth")
                        .description("JWT token")
                        .scheme("bearer")
                        .type(SecurityScheme.Type.HTTP)
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER);

                return new OpenAPI()
                        .info(new Info().title("VideoHosting API")
                                .version("v0.0.1"))
                        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme));

        }
}
