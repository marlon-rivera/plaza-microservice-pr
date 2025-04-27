package com.pragma.plaza_service.infrastructure.documentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Plaza Service API",
                description = "API documentation for the Plaza Service",
                version = "${springdoc.version:1.0.0}",
                contact = @Contact(
                        name = "Marlon Rivera",
                        email = "marlon.rivera@pragma.com.co"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class OpenApiConfiguration {
}