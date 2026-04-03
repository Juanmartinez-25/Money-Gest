package com.moneygest.Service;

import com.moneygest.Model.Usuario;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos al usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + email));

        // Normalizamos el rol para Spring Security
        String nombreRol = usuario.getRol().getNombre().toUpperCase();
        if (!nombreRol.startsWith("ROLE_")) {
            nombreRol = "ROLE_" + nombreRol;
        }

        // Construimos el UserDetails usando isActivo() del modelo
        return new User(
                usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.isActivo(), // <--- Ahora sí existe en el modelo
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority(nombreRol))
        );
    }
}