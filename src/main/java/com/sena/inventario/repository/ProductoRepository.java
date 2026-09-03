package com.sena.inventario.repository;

import com.sena.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de acceso a datos para Producto.
 * Spring Data JPA genera automaticamente la implementacion de estos
 * metodos reutilizables a partir de su firma (query methods).
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaIgnoreCase(String categoria);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    Optional<Producto> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Producto> findByCantidadStockLessThanEqual(Integer umbral);
}
