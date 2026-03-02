package com.unicoconnect.issuetracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI issueTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Issue Tracker API")
                        .description(
                                "REST API for the Unico Connect Issue Tracker — manage issues, comments, and exports.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Unico Connect")
                                .email("support@unicoconnect.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development")));
    }
}
