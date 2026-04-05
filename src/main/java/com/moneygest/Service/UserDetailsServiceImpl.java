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

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UsernameNotFoundException("El usuario está desactivado.");
        }


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