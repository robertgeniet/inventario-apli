package com.sena.inventario.service.impl;

import com.sena.inventario.dto.AuthResponse;
import com.sena.inventario.dto.LoginRequest;
import com.sena.inventario.dto.RegistroRequest;
import com.sena.inventario.exception.BusinessException;
import com.sena.inventario.model.Usuario;
import com.sena.inventario.repository.UsuarioRepository;
import com.sena.inventario.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementacion de los servicios de registro e inicio de sesion.
 * La contrasena jamas se compara ni se guarda en texto plano.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new BusinessException("Ya existe una cuenta registrada con el correo: " + request.getCorreo());
        }
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new BusinessException("El nombre de usuario '" + request.getNombreUsuario() + "' ya esta en uso");
        }

        String hash = passwordEncoder.encode(request.getContrasena());
        Usuario usuario = new Usuario(request.getNombreUsuario(), request.getCorreo(), hash);
        usuario = usuarioRepository.save(usuario);

        return new AuthResponse(usuario.getId(), usuario.getNombreUsuario(), usuario.getCorreo(),
                "Usuario registrado exitosamente");
    }

    @Override
    public AuthResponse iniciarSesion(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new BusinessException("Correo o contrasena incorrectos"));

        boolean coincide = passwordEncoder.matches(request.getContrasena(), usuario.getContrasenaHash());
        if (!coincide) {
            throw new BusinessException("Correo o contrasena incorrectos");
        }

        return new AuthResponse(usuario.getId(), usuario.getNombreUsuario(), usuario.getCorreo(),
                "Inicio de sesion exitoso");
    }
}
