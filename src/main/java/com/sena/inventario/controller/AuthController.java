package com.sena.inventario.controller;

import com.sena.inventario.dto.AuthResponse;
import com.sena.inventario.dto.LoginRequest;
import com.sena.inventario.dto.RegistroRequest;
import com.sena.inventario.service.AuthService;
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
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /api/auth/registro -> crea una cuenta nueva
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        AuthResponse respuesta = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // POST /api/auth/login -> valida credenciales e inicia sesion
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(@Valid @RequestBody LoginRequest request) {
        AuthResponse respuesta = authService.iniciarSesion(request);
        return ResponseEntity.ok(respuesta);
    }
}
