package com.sena.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del proyecto.
 * GA7-220501096-AA5-EV03 - Diseno y desarrollo de servicios web.
 * Expone servicios web REST reutilizables para la gestion de inventario/productos.
 */
@SpringBootApplication
public class InventarioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventarioApiApplication.class, args);
    }
}
