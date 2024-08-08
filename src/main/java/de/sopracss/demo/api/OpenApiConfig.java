package de.sopracss.demo.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // this hase the same result as setting @OpenAPIDefinition
    //@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo API")
                        .version("1.0")
                ).components(
                        new Components().addSecuritySchemes(
                                "basicAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                ).addSecurityItem(
                        new SecurityRequirement().addList("basicAuth")
                );
    }
}
