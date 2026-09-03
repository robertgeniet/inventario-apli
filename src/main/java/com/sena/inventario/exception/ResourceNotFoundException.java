package com.sena.inventario.exception;

/**
 * Excepcion lanzada cuando un producto solicitado no existe en el inventario.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
