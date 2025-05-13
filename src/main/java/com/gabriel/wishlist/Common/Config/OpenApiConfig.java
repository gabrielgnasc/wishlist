package com.gabriel.wishlist.Common.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8082");
        server.setDescription("Servidor de Produção - loadbalance");

        return new OpenAPI()
                .info(new Info()
                        .title("Wishlist Service API")
                        .version("1.0.0")
                        .description("API para gerenciamento de lista de desejos de e-commerce")
                        .license(new License().name("Apache 2.0")))
                .addServersItem(server);
    }
}