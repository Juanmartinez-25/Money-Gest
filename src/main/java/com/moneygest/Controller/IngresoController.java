package com.moneygest.Controller;

import com.moneygest.Model.Ingreso;
import com.moneygest.Service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @PostMapping
    public ResponseEntity<?> registrarIngreso(@RequestBody Ingreso ingreso) {
        try {
            Ingreso nuevoIngreso = ingresoService.guardarIngreso(ingreso);
            return ResponseEntity.ok(nuevoIngreso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al guardar: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Ingreso>> listarTodos() {
        return ResponseEntity.ok(ingresoService.listarIngresos());
    }

    @GetMapping("/total")
    public ResponseEntity<?> obtenerTotal() {
        return ResponseEntity.ok(Map.of("total", ingresoService.obtenerTotalIngresos()));
    }
}