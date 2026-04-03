package com.moneygest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador exclusivo para el acceso inicial al sistema.
 */
@Controller
public class LoginController {

    /**
     * Muestra la pantalla de inicio de sesión (index.html).
     */
    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }

    // Nota: Las rutas de /recuperar se movieron a MenuPrincipalController
    // para centralizar la lógica y evitar errores de mapeo duplicado.
}