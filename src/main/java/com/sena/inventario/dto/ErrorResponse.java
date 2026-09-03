package com.sena.inventario.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura estandar de respuesta cuando ocurre un error,
 * ya sea de validacion o de negocio.
 */
public class ErrorResponse {

    private LocalDateTime fecha;
    private int estado;
    private String error;
    private List<String> mensajes;
    private String ruta;

    public ErrorResponse(int estado, String error, List<String> mensajes, String ruta) {
        this.fecha = LocalDateTime.now();
        this.estado = estado;
        this.error = error;
        this.mensajes = mensajes;
        this.ruta = ruta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getEstado() {
        return estado;
    }

    public String getError() {
        return error;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public String getRuta() {
        return ruta;
    }
}
