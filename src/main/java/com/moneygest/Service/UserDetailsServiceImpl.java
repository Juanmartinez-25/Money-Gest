package com.moneygest.Service;

import com.moneygest.Model.Usuario;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Buscamos el usuario usando tu entidad Usuario y el repositorio
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario: " + correo));

        // Verificamos si el usuario está activo (campo 'activo' de tu entidad)
        if (!usuario.isActivo()) {
            throw new UsernameNotFoundException("El usuario está desactivado.");
        }

        // Retornamos el usuario para que Spring Security compare la contraseña
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena()) // Usa tu getter getContrasena()
                .roles(usuario.getRol().getNombre()) // Asigna el rol desde la relación
                .build();
    }
}