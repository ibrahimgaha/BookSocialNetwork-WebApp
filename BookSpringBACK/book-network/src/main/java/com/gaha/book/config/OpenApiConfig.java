package com.gaha.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Ibrahim Gaha", email = "Ibrahimgaha2000@Gmail.com"), description = "OpenApi Documentation For Spring Documentation", title = "OpenApi Specification - Ibrahim Gaha", version = "1.0", license = @License(name = "Your License Name"), termsOfService = "Your Terms Of Service URL"), servers = {
		@Server(description = "Local ENV", url = "http://localhost:8088"),
		@Server(description = "Prod ENV", url = "http://your-production-url.com") }, security = {
				@SecurityRequirement(name = "bearerAuth") })

@SecurityScheme(name = "bearerAuth", description = "JWT Auth Description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}
