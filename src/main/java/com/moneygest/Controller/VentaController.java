package com.moneygest.Controller;

import com.moneygest.Model.Venta;
import com.moneygest.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importación necesaria
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String mostrarGestionVentas(Model model) {
        // Esta línea es la que soluciona el error:
        // Le dice a Thymeleaf: "Aquí tienes el objeto 'venta' para el formulario"
        model.addAttribute("venta", new Venta());

        return "gestionVentas";
    }

    // ... tus otros métodos (@PostMapping, etc.)
}