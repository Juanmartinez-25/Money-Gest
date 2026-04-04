package com.moneygest.Controller;

import com.moneygest.Model.Rol;
import com.moneygest.Model.Usuario;
import com.moneygest.Service.UsuarioService;
import com.moneygest.Repository.RolRepository;
import com.moneygest.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UsuarioService usuarioService;
    @Autowired private RolRepository rolRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping
    public String mostrarPanelAdmin(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("roles", rolRepository.findAll());
        return "administracion";
    }

    @PostMapping("/usuarios/editar-completo")
    public String editarUsuario(@RequestParam("id") Integer id,
                                @RequestParam("nuevoRol") String nuevoRol,
                                @RequestParam(value = "nuevaPassword", required = false) String nuevaPassword,
                                RedirectAttributes ra) {
        Usuario u = usuarioRepository.findById(id).orElse(null);
        if (u != null) {
            Rol rol = rolRepository.findByNombre(nuevoRol);
            if (rol != null) {
                u.setIdRol(rol.getId());
            }

            // Si escribiste una clave nueva, la actualiza y APAGA la alerta
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                u.setContrasena(nuevaPassword);
                u.setSolicitudCambioClave(false);
            }

            usuarioRepository.save(u);
            ra.addFlashAttribute("mensaje", "Usuario actualizado correctamente.");
        }
        return "redirect:/admin";
    }

    @PostMapping("/usuarios/crear")
    public String crear(@RequestParam String nombre, @RequestParam String correo,
                        @RequestParam String contrasena, @RequestParam String confirmarContrasena,
                        RedirectAttributes ra) {
        if(!contrasena.equals(confirmarContrasena)) {
            ra.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "redirect:/admin";
        }
        try {
            Usuario n = new Usuario();
            n.setNombre(nombre);
            n.setCorreo(correo);
            n.setContrasena(contrasena);
            n.setActivo(true);

            // 👇 ESTO SOLUCIONA TU ERROR (SQL 1048) 👇
            n.setSolicitudCambioClave(false);

            Rol rolDefault = rolRepository.findByNombre("ROLE_USER");
            if (rolDefault != null) {
                n.setIdRol(rolDefault.getId());
            }

            usuarioRepository.save(n);
            ra.addFlashAttribute("mensaje", "Usuario registrado con éxito.");
        } catch(Exception e) {
            ra.addFlashAttribute("error", "El correo ya existe o hubo un error en la BD.");
        }
        return "redirect:/admin";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes ra) {
        usuarioRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Usuario eliminado.");
        return "redirect:/admin";
    }
}