package com.sena.inventario.service;

import com.sena.inventario.dto.AuthResponse;
import com.sena.inventario.dto.LoginRequest;
import com.sena.inventario.dto.RegistroRequest;

/**
 * Servicios reutilizables de autenticacion: registro de nuevos
 * usuarios e inicio de sesion.
 */
public interface AuthService {

    AuthResponse registrar(RegistroRequest request);

    AuthResponse iniciarSesion(LoginRequest request);
}
