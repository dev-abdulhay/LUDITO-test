package org.ludito.app.config.doc;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@Configuration
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        name = "X-Auth-Token",
        scheme = "X-Auth-Token"
)
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "X-Auth-Token"),
        info = @Info(description = "Admin uchun API", version = "v1", title = "LUDITO",
                contact = @Contact(name = "+998 99 007 56 03")
        ),
        servers = {
                @Server(url = "http://localhost:8081/"),
        }
)
public class DocConfig {
}
