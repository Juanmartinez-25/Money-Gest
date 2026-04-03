package com.moneygest.Service;

import com.moneygest.Model.Rol;
import com.moneygest.Model.Usuario;
import com.moneygest.Repository.RolRepository;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Busca el usuario por correo [cite: 110, 114]
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // Verifica si está activo (usando el nuevo getter de Integer/Boolean) [cite: 110]
        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UsernameNotFoundException("El usuario está desactivado.");
        }

        // Obtiene el nombre del rol desde la tabla roles usando el id_rol [cite: 12, 80, 82]
        String nombreRol = "USER";
        if (usuario.getIdRol() != null) {
            Rol rol = rolRepository.findById(usuario.getIdRol()).orElse(null);
            if (rol != null && rol.getNombre() != null) {
                nombreRol = rol.getNombre().replace("ROLE_", "");
            }
        }

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena())
                .roles(nombreRol)
                .build();
    }
}