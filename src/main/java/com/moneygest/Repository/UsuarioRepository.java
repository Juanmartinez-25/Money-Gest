package com.moneygest.Repository;

import com.moneygest.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { // Cambiado Long a Integer
    Optional<Usuario> findByCorreo(String correo);
}