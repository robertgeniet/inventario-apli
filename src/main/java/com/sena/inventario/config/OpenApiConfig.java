package com.sena.inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura la informacion general que se muestra en la documentacion
 * interactiva de la API (Swagger UI), disponible en /swagger-ui.html
 * una vez la aplicacion esta corriendo.
 * GA7-220501096-AA5-EV04: documentacion de las APIs del proyecto.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventario API - GA7-220501096-AA5")
                        .description("Servicios web REST para la gestion de inventario/productos "
                                + "y la autenticacion de usuarios (registro e inicio de sesion). "
                                + "Proyecto SENA: Diseno y desarrollo de servicios web.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Estudiante SENA")
                                .email("estudiante@sena.edu.co")));
    }
}
