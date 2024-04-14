package silver.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "PRODUCT API REST",
                description = "Basic application of products with a focus on functionality",
                version = "1.0",
                license = @License(
                        name = "MIT License",
                        url= "https://choosealicense.com/licenses/mit/"),
                termsOfService = "Terms of service",
                contact = @Contact(
                        name = "dagsan",
                        email = "santidaglio@gmail.com",
                        url = "https://dagsan.dev"
                )
        ),
        servers = {
                @Server(
                        description = "Local environment API server",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig {}