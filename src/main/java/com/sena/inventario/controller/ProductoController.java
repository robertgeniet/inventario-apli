package com.sena.inventario.controller;

import com.sena.inventario.model.Producto;
import com.sena.inventario.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Productos", description = "Gestion del inventario: crear, consultar, actualizar y eliminar productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Lista todos los productos del inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @Operation(summary = "Obtiene un producto por su id")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "No existe un producto con ese id")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@Parameter(description = "Id del producto") @PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation(summary = "Filtra productos por categoria")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> buscarPorCategoria(
            @Parameter(description = "Nombre de la categoria") @PathVariable String categoria) {
        return ResponseEntity.ok(productoService.buscarPorCategoria(categoria));
    }

    @Operation(summary = "Busca productos cuyo nombre contenga el texto dado")
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @Parameter(description = "Texto a buscar en el nombre") @RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Lista productos con stock igual o menor al umbral indicado")
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> stockBajo(
            @Parameter(description = "Umbral de stock bajo, por defecto 5")
            @RequestParam(defaultValue = "5") Integer umbral) {
        return ResponseEntity.ok(productoService.listarConStockBajo(umbral));
    }

    @Operation(summary = "Calcula el valor monetario total del inventario (precio x cantidad de cada producto)")
    @GetMapping("/valor-total")
    public ResponseEntity<Map<String, BigDecimal>> valorTotalInventario() {
        return ResponseEntity.ok(Map.of("valorTotalInventario", productoService.calcularValorTotalInventario()));
    }

    @Operation(summary = "Crea un producto nuevo",
            description = "Valida los datos (nombre, precio, stock, SKU) antes de guardar. "
                    + "El SKU debe ser unico en todo el inventario.")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @ApiResponse(responseCode = "400", description = "Datos invalidos (ver mensajes de validacion)")
    @ApiResponse(responseCode = "409", description = "El SKU ya existe")
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        Producto creado = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualiza todos los datos de un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "No existe un producto con ese id")
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @Operation(summary = "Ajusta el stock de un producto",
            description = "Un valor positivo suma stock, un valor negativo lo resta. "
                    + "No permite dejar el stock en negativo.")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> ajustarStock(@PathVariable Long id,
            @Parameter(description = "Cantidad a sumar (positivo) o restar (negativo)") @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.ajustarStock(id, cantidad));
    }

    @Operation(summary = "Elimina un producto del inventario")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
