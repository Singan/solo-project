package com.my.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "My Project API",
                description = "My Project API 명세서",
                version = "v1"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", scheme = "bearer")
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi myProjectOpenApi() {
        String[] paths = {"/board/**","**"};
        return GroupedOpenApi.builder()
                .group("MyProject API")
                .pathsToMatch(paths)
                .build();
    }

}
