package com.my.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
        info = @Info(title = "My Project API",
                description = "My Project API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        String authSchemeName = "X-AUTH-TOKEN";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(authSchemeName);

        Components components = new Components()
                .addSecuritySchemes(authSchemeName, new SecurityScheme()
                        .name(authSchemeName)
                        .type(SecurityScheme.Type.APIKEY)  // API Key 타입을 사용합니다.
                        .in(SecurityScheme.In.HEADER)      // 헤더에 포함됩니다.
                        .name(authSchemeName));             // 헤더 이름을 X-AUTH-TOKEN으로 설정합니다.

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}