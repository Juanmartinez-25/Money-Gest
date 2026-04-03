package com.moneygest.Controller;

import com.moneygest.Service.InformeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/informes")
public class InformeController {

    private final InformeService informeService;

    public InformeController(InformeService informeService) {
        this.informeService = informeService;
    }

    @GetMapping
    public String verInformes(Model model) {

        model.addAttribute("totalIngresos", informeService.totalIngresos());
        model.addAttribute("totalGastos", informeService.totalGastos());
        model.addAttribute("balance", informeService.balanceGeneral());

        return "informes";
    }
}
