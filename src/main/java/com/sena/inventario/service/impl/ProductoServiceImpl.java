package com.sena.inventario.service.impl;

import com.sena.inventario.exception.BusinessException;
import com.sena.inventario.exception.ResourceNotFoundException;
import com.sena.inventario.model.Producto;
import com.sena.inventario.repository.ProductoRepository;
import com.sena.inventario.service.ProductoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementacion de los servicios reutilizables de inventario.
 * Toda la logica de negocio (reglas, validaciones adicionales a las
 * anotaciones del modelo, calculos) vive aqui, separada del controlador
 * REST, para que sea reutilizable desde cualquier punto del sistema.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public Producto crear(Producto producto) {
        if (productoRepository.existsBySku(producto.getSku())) {
            throw new BusinessException("Ya existe un producto con el SKU: " + producto.getSku());
        }
        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public Producto actualizar(Long id, Producto datosActualizados) {
        Producto existente = obtenerPorId(id);

        // Verifica que el nuevo SKU no choque con el de otro producto distinto
        productoRepository.findBySku(datosActualizados.getSku()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new BusinessException("El SKU " + datosActualizados.getSku() + " ya esta en uso por otro producto");
            }
        });

        existente.setNombre(datosActualizados.getNombre());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setCategoria(datosActualizados.getCategoria());
        existente.setPrecio(datosActualizados.getPrecio());
        existente.setCantidadStock(datosActualizados.getCantidadStock());
        existente.setSku(datosActualizados.getSku());

        return productoRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Producto existente = obtenerPorId(id);
        productoRepository.delete(existente);
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un producto con id: " + id));
    }

    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoriaIgnoreCase(categoria);
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> listarConStockBajo(Integer umbral) {
        return productoRepository.findByCantidadStockLessThanEqual(umbral);
    }

    @Override
    @Transactional
    public Producto ajustarStock(Long id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        int nuevaCantidad = producto.getCantidadStock() + cantidad;

        if (nuevaCantidad < 0) {
            throw new BusinessException("La operacion dejaria el stock en negativo para el producto: " + producto.getNombre());
        }
        producto.setCantidadStock(nuevaCantidad);
        return productoRepository.save(producto);
    }

    @Override
    public BigDecimal calcularValorTotalInventario() {
        return productoRepository.findAll().stream()
                .map(p -> p.getPrecio().multiply(BigDecimal.valueOf(p.getCantidadStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
