package com.moneygest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }


}