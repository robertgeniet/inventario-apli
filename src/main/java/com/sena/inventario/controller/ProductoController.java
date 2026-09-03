package com.sena.inventario.controller;

import com.sena.inventario.model.Producto;
import com.sena.inventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * API REST para la gestion de inventario/productos.
 * Base: /api/productos
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // GET /api/productos -> lista completa
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    // GET /api/productos/{id} -> obtiene uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // GET /api/productos/categoria/{categoria}
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
    }

    // GET /api/productos/buscar?nombre=xxx
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    // GET /api/productos/stock-bajo?umbral=5
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> stockBajo(@RequestParam(defaultValue = "5") Integer umbral) {
        return ResponseEntity.ok(productoService.listarConStockBajo(umbral));
    }

    // GET /api/productos/valor-total -> valor monetario total del inventario
    @GetMapping("/valor-total")
    public ResponseEntity<Map<String, BigDecimal>> valorTotalInventario() {
        return ResponseEntity.ok(Map.of("valorTotalInventario", productoService.calcularValorTotalInventario()));
    }

    // POST /api/productos -> crea un producto (con validacion @Valid)
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto creado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/productos/{id} -> actualiza un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    // PATCH /api/productos/{id}/stock?cantidad=10  (positivo suma, negativo resta)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> ajustarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.ajustarStock(id, cantidad));
    }

    // DELETE /api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
