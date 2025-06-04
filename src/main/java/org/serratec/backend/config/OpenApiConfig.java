package org.serratec.backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${api.openapi.dev-url:http://localhost:8080}") 
	private String devUrl;

	@Value("${api.openapi.prod-url:https://sua-api-em-producao.com}")
	private String prodUrl;

	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Servidor de Desenvolvimento");

		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("Servidor de Produção");

		Contact contact = new Contact();
		contact.setEmail("seuemail@exemplo.com");
		contact.setName("Nome do Contato ou Empresa");
		contact.setUrl("https://seusite.com");

		License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

		Info info = new Info().title("API E-Commerce - Projeto Final").version("1.0")
				.description("Esta API gerencia as operações de um sistema de E-Commerce.").contact(contact)
				.license(mitLicense).termsOfService("https://seusite.com/termos");

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}
}