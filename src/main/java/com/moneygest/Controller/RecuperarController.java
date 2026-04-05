package com.moneygest.Controller;

import com.moneygest.Model.Usuario;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecuperarController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/recuperar/enviar")
    public String enviarSolicitud(@RequestParam String correo, RedirectAttributes ra) {
        usuarioRepository.findByCorreo(correo).ifPresentOrElse(u -> {

            u.setSolicitudCambioClave(true);
            usuarioRepository.save(u);

            ra.addFlashAttribute("mensaje", "Solicitud enviada al administrador exitosamente.");
        }, () -> {
            ra.addFlashAttribute("error", "Ese correo no está registrado en nuestro sistema.");
        });

        return "redirect:/recuperar"; // Redirige a la ruta correcta
    }
}