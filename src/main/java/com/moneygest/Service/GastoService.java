package com.moneygest.Service;

import com.moneygest.Model.Gasto;
import com.moneygest.Repository.GastoRepository;
import com.moneygest.Repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {
    @Autowired
    private IngresoRepository ingresoRepository;
    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> obtenerTodosLosGastos() {
        return gastoRepository.findAll();
    }

    public Gasto guardarGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    public Double calcularSaldoDisponible() {
        Double gastosTotales = gastoRepository.totalGastos();
        Double ingresosReales = ingresoRepository.totalIngresos();
        return ingresosReales - gastosTotales;
    }
}