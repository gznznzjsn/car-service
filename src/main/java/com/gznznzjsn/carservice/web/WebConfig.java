package com.gznznzjsn.carservice.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(title = "Car Service API", version = "1.0"),
        servers = {@Server(url = "http://localhost:8080", description = "Local server")},
        tags = {
                @Tag(name = "Authentication", description = "Registration, authentication and token management"),
                @Tag(name = "Orders", description = "Order management, you must be authenticated"),
                @Tag(name = "Assignments", description = "Assignment management, you must be authenticated"),
                @Tag(name = "Employees", description = "Employee management, you must have employee manager role"),
        }
)
public class WebConfig {
}