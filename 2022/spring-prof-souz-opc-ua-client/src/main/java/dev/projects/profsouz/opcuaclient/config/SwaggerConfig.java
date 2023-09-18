package dev.projects.profsouz.opcuaclient.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api(@Value("${app.swagger.title}") String title,
                       @Value("${app.swagger.description}") String description,
                       @Value("${app.swagger.version}") String version) {
        return new OpenAPI().info(apiInfo(title, description, version));
    }

    private Info apiInfo(String title, String description, String version) {
        return new Info().title(title).description(description).version(version);
    }
}