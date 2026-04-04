package com.moneygest.Controller;

import com.moneygest.Model.Venta;
import com.moneygest.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String mostrarGestionVentas(Model model) {
        model.addAttribute("venta", new Venta());
        return "gestionVentas";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta) {
        // Truco: Copiamos el valor del total al monto para llenar ambas columnas
        if(venta.getTotal() != null) {
            venta.setMonto(venta.getTotal());
        } else {
            venta.setMonto(0.0);
            venta.setTotal(0.0);
        }

        ventaService.guardarVentaSimple(venta);
        return "redirect:/ventas";
    }
}