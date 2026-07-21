package com.example.gymmembershipapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymMembershipOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gym Membership API")
                        .description(
                                "REST API for managing trainers, members, " +
                                        "training programs and subscriptions"
                        )
                        .version("1.0"));
    }
}