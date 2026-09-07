package com.sena.inventario.dto;

/**
 * Respuesta que se devuelve tras un registro o inicio de sesion exitoso.
 * Nunca incluye la contrasena ni su hash, solo datos publicos del usuario.
 */
public class AuthResponse {

    private Long id;
    private String nombreUsuario;
    private String correo;
    private String mensaje;

    public AuthResponse(Long id, String nombreUsuario, String correo, String mensaje) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getMensaje() {
        return mensaje;
    }
}
