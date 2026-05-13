package com.danieldev87.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Yogurt Maker API")
                        .version("1.0.0")
                        .description("API para la gestión del proceso de fabricación de yogurt")
                        .contact(new Contact()
                                .name("Yogurt Maker")
                                .url("https://yogurtmaker.com")));
    }
}

