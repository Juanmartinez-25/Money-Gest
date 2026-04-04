package com.moneygest.Controller;

import com.moneygest.Service.InformeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/informes")
public class InformeController {

    private final InformeService informeService;

    public InformeController(InformeService informeService) {
        this.informeService = informeService;
    }

    @GetMapping
    public String verPanel(Model model) {
        // Sumamos todas las entradas (Capital + Ventas) para mostrar "Total Ingresos"
        double entradasTotales = informeService.totalI() + informeService.totalV();

        model.addAttribute("totalIngresos", entradasTotales);
        model.addAttribute("totalGastos", informeService.totalG());
        model.addAttribute("balance", informeService.obtenerSaldoReal()); // El valor ya restado

        return "informes";
    }

    @PostMapping("/descargar")
    public void generarReporte(@RequestParam("fechaDesde") String desde,
                               @RequestParam("fechaHasta") String hasta,
                               HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reporte_MoneyGest.pdf");

        LocalDateTime inicio = LocalDate.parse(desde).atStartOfDay();
        LocalDateTime fin = LocalDate.parse(hasta).atTime(23, 59, 59);

        informeService.descargarPdf(response, inicio, fin);
    }
}