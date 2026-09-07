package com.sena.inventario.controller;

import com.sena.inventario.dto.AuthResponse;
import com.sena.inventario.dto.LoginRequest;
import com.sena.inventario.dto.RegistroRequest;
import com.sena.inventario.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST de autenticacion.
 * Base: /api/auth
 * GA7-220501096-AA5-EV01: servicio de registro + servicio de inicio de sesion.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticacion", description = "Registro de usuarios e inicio de sesion")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Registra un usuario nuevo",
            description = "Valida correo, nombre de usuario y fortaleza de la contrasena. "
                    + "La contrasena se guarda encriptada (hash BCrypt), nunca en texto plano.")
    @ApiResponse(responseCode = "201", description = "Usuario registrado")
    @ApiResponse(responseCode = "400", description = "Datos invalidos (ver mensajes de validacion)")
    @ApiResponse(responseCode = "409", description = "El correo o nombre de usuario ya estan en uso")
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        AuthResponse respuesta = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Inicia sesion con correo y contrasena",
            description = "Compara la contrasena recibida contra el hash almacenado. "
                    + "No revela si el error fue el correo o la contrasena, por seguridad.")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso")
    @ApiResponse(responseCode = "400", description = "Datos invalidos (ver mensajes de validacion)")
    @ApiResponse(responseCode = "409", description = "Correo o contrasena incorrectos")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        AuthResponse respuesta = authService.iniciarSesion(request);
        return ResponseEntity.ok(respuesta);
    }
}
