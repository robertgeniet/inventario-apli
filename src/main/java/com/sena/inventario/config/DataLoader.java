package com.sena.inventario.config;

import com.sena.inventario.model.Producto;
import com.sena.inventario.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Carga algunos productos de ejemplo al iniciar la aplicacion,
 * unicamente para facilitar las pruebas manuales de la API.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public DataLoader(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto("Teclado mecanico", "Teclado mecanico retroiluminado",
                    "Perifericos", new BigDecimal("189900"), 25, "TEC-001"));
            productoRepository.save(new Producto("Mouse inalambrico", "Mouse inalambrico ergonomico",
                    "Perifericos", new BigDecimal("79900"), 40, "MOU-002"));
            productoRepository.save(new Producto("Monitor 24 pulgadas", "Monitor Full HD 24\"",
                    "Monitores", new BigDecimal("649900"), 3, "MON-003"));
        }
    }
}
