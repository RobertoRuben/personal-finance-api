package com.betooo.personalfinanceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiConfig {

    @Value("${personal-finance-api.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development Server for Personal Finance API");

        Contact contact = new Contact();
        contact.setName("Betooo");
        contact.setEmail("robertoch263@gmail.com");

        License mitLicense = new License()
                .name("Licencia MIT")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Personal Finance API")
                .version("1.0.0")
                .contact(contact)
                .description("API for managing personal finances, including income, expenses, and budgets.")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);
    }
}
