package com.sena.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura el codificador de contrasenas usado por el servicio de
 * autenticacion. BCrypt genera un hash distinto cada vez (usa "sal"
 * aleatoria), por eso nunca se puede revertir ni comparar por igualdad
 * directa: se usa passwordEncoder.matches(...) para verificar.
 */
@Configuration
public class SeguridadConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
