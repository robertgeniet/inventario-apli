package com.sena.inventario.exception;

/**
 * Excepcion lanzada cuando se viola una regla de negocio,
 * por ejemplo un SKU duplicado o una operacion de stock invalida.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
