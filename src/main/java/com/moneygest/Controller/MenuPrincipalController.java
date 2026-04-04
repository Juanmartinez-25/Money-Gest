package com.moneygest.Controller;

import com.moneygest.Model.Gasto;
import com.moneygest.Model.Usuario;
import com.moneygest.Service.GastoService;
import com.moneygest.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuPrincipalController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/menuPrincipal")
    public String mostrarHome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String nombreMostrar = "Usuario"; // Valor por defecto si todo falla

        if (currentUser != null) {
            Usuario u = usuarioService.obtenerPorCorreo(currentUser.getUsername());

            // Validamos que el usuario exista y tenga un nombre guardado en la BD
            if (u != null && u.getNombre() != null && !u.getNombre().isEmpty()) {
                nombreMostrar = u.getNombre();
            } else if (currentUser.getUsername() != null) {
                // Si la columna nombre es NULL, usamos la primera parte de su correo
                nombreMostrar = currentUser.getUsername().split("@")[0];
            }
        }

        model.addAttribute("nombreUsuario", nombreMostrar);
        // Eliminamos las variables estáticas de las asignaciones

        return "menuPrincipal";
    }

    @GetMapping("/gastos")
    public String mostrarGastos(Model model) {
        model.addAttribute("gastos", gastoService.obtenerTodosLosGastos());
        model.addAttribute("saldo", gastoService.calcularSaldoDisponible());
        model.addAttribute("gasto", new Gasto());
        return "gestionGastos";
    }

    @GetMapping("/capital")
    public String mostrarCapital(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            Usuario u = usuarioService.obtenerPorCorreo(currentUser.getUsername());
            if (u != null) {
                model.addAttribute("idUsuarioLogueado", u.getId());
                model.addAttribute("esSocio", u.getIdRol() == 2);
            }
        }
        return "ingresoCapital";
    }

    @GetMapping("/logout")
    public String cerrarSesion() { return "redirect:/?logout"; }

    @GetMapping("/recuperar")
    public String mostrarVistaRecuperar() { return "recuperar-password"; }
}