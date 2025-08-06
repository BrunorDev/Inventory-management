package com.example.inventory_api.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory Management")
                        .version("1.0")
                        .description("This API was created so that companies can manage their inventory efficiently.")
                        .contact(new Contact()
                                .name("Bruno Reis de Oliveira Silva")
                                .url("https://github.com/BrunorDev")));
    }
}
