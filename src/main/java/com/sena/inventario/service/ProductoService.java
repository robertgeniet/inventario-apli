package com.sena.inventario.service;

import com.sena.inventario.model.Producto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrato de servicios reutilizables de gestion de inventario.
 * Estos metodos encapsulan la logica de negocio para que puedan
 * ser invocados tanto desde los controladores REST como desde
 * cualquier otro modulo del software (reutilizacion).
 */
public interface ProductoService {

    Producto crear(Producto producto);

    Producto actualizar(Long id, Producto producto);

    void eliminar(Long id);

    Producto obtenerPorId(Long id);

    List<Producto> listarTodos();

    List<Producto> buscarPorCategoria(String categoria);

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> listarConStockBajo(Integer umbral);

    Producto ajustarStock(Long id, Integer cantidad);

    BigDecimal calcularValorTotalInventario();
}
