package com.moneygest.Controller;

import com.moneygest.Model.Venta;
import com.moneygest.Service.UsuarioService;
import com.moneygest.Service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final UsuarioService usuarioService;

    public VentaController(VentaService ventaService, UsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.usuarioService = usuarioService;
    }

    // Mostrar todas las ventas en la tabla de Mercaplasticos
    @GetMapping
    public String verVentas(Model model) {
        // Carga la lista de ventas y el cálculo del total global
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("totalVentas", ventaService.totalVentas());

        // Carga los empleados para el <select> del modal
        model.addAttribute("usuarios", usuarioService.listarTodos());

        model.addAttribute("venta", new Venta());
        return "gestionVentas";
    }

    // Guardar nueva venta con Factura y Método de Pago
    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta,
                               @RequestParam Integer usuarioId,
                               RedirectAttributes redirectAttrs) {
        if (usuarioId == null) {
            redirectAttrs.addFlashAttribute("error", "Debe seleccionar un usuario válido");
            return "redirect:/ventas";
        }

        try {
            // Asigna el usuario (Integer) y guarda la venta
            venta.setUsuario(usuarioService.obtenerUsuario(usuarioId));
            ventaService.guardarVenta(venta);
            redirectAttrs.addFlashAttribute("mensaje", "Venta registrada con éxito");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }

        return "redirect:/ventas";
    }

    // Actualizar venta (Edición rápida)
    @PostMapping("/actualizar")
    public String actualizarVenta(@ModelAttribute Venta venta,
                                  @RequestParam Integer usuarioId,
                                  RedirectAttributes redirectAttrs) {
        if (venta.getId() == null || usuarioId == null) {
            redirectAttrs.addFlashAttribute("error", "Datos de actualización inválidos.");
            return "redirect:/ventas";
        }
        venta.setUsuario(usuarioService.obtenerUsuario(usuarioId));
        ventaService.guardarVenta(venta);
        return "redirect:/ventas";
    }

    // Eliminar registro
    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Integer id) {
        ventaService.eliminarVenta(id);
        return "redirect:/ventas";
    }
}