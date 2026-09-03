package com.sena.inventario.exception;

import com.sena.inventario.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones de la API.
 * Centraliza la construccion de respuestas de error consistentes,
 * incluyendo los mensajes de las validaciones de los DTO/entidades.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Se dispara cuando @Valid detecta que un objeto recibido no cumple las validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex,
                                                              HttpServletRequest request) {
        List<String> mensajes = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validacion",
                mensajes,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(ResourceNotFoundException ex,
                                                               HttpServletRequest request) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> manejarNegocio(BusinessException ex,
                                                          HttpServletRequest request) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Regla de negocio violada",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(Exception ex, HttpServletRequest request) {
        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
