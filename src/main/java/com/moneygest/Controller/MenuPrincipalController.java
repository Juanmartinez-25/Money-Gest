package com.moneygest.Controller;

import com.moneygest.Model.Gasto;
import com.moneygest.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MenuPrincipalController {
    @Autowired
    private GastoService gastoService;
    @GetMapping("/menuPrincipal")
    public String mostrarHome(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        if (currentUser != null) {
            model.addAttribute("usuarioLogueado", currentUser.getUsername());
        }
        // Datos informativos para el Dashboard
        model.addAttribute("asignacionesActivas", "100%");
        model.addAttribute("porcentajeAsignacion", "85%");
        model.addAttribute("cumplimientoMeses", "92%");
        return "menuPrincipal";
    }
    @GetMapping("/gastos")
    public String mostrarGastos(Model model) {
        // Estas 3 líneas alimentan al HTML y evitan el error 500
        model.addAttribute("gastos", gastoService.obtenerTodosLosGastos());
        model.addAttribute("saldo", gastoService.calcularSaldoDisponible());
        model.addAttribute("gasto", new Gasto());
        return "gestionGastos";
    }
    @GetMapping("/capital")
    public String mostrarCapital() { return "ingresoCapital"; }


    @GetMapping("/logout")
    public String cerrarSesion() { return "redirect:/?logout"; }

    /**
     * Vista de recuperación (informativa)
     */
    @GetMapping("/recuperar")
    public String mostrarVistaRecuperar() {
        return "recuperar-password";
    }

    /**
     * Procesa la solicitud y redirige al panel de gestión de usuarios
     */
    @PostMapping("/recuperar")
    public String procesarRecuperacion(@RequestParam("correo") String correoUsuario,
                                       RedirectAttributes redirectAttrs) {

        // Simplemente redirigimos al administrador a la vista de gestión
        redirectAttrs.addFlashAttribute("mensaje", "Solicitud recibida para " + correoUsuario + ". Por favor, gestiona el cambio de contraseña en este panel.");

        return "redirect:/usuarios/gestion";
    }

}