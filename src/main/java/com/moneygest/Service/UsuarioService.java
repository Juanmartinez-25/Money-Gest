package com.moneygest.Service;

import com.moneygest.Model.Usuario;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuario(Integer id) {
        if (id == null) return null;
        return usuarioRepository.findById(id).orElse(null); // Ahora sí coinciden los tipos
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }
}