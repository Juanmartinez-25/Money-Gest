package com.moneygest.Controller;

import com.moneygest.Model.Gasto;
import com.moneygest.Service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "*")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    public ResponseEntity<List<Gasto>> listarGastos() {
        return new ResponseEntity<>(gastoService.obtenerTodosLosGastos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Gasto> crearGasto(@RequestBody Gasto gasto) {
        return new ResponseEntity<>(gastoService.guardarGasto(gasto), HttpStatus.CREATED);
    }

    @GetMapping("/saldo")
    public ResponseEntity<Map<String, Double>> obtenerSaldo() {
        Map<String, Double> respuesta = new HashMap<>();
        respuesta.put("saldoDisponible", gastoService.calcularSaldoDisponible());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}