package com.sena.inventario.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario del sistema, usada para
 * el registro y el inicio de sesion (EV01).
 * La contrasena NUNCA se guarda en texto plano: siempre se
 * almacena su hash (ver AuthServiceImpl).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombreUsuario;

    @Column(nullable = false, length = 100, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasenaHash;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String correo, String contrasenaHash) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
