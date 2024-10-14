package fr.diginamic.hello.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui.html

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation - City Management")
                        .version("1.0")
                        .description("Documentation de l'API pour la gestion des villes")
                        .termsOfService("https://www.example.com/terms")
                        .contact(new Contact()
                                .name("Support API")
                                .email("justineragues@gmail.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
