package com.moneygest.Service;

import com.moneygest.Repository.GastoRepository;
import com.moneygest.Repository.IngresoRepository;
import org.springframework.stereotype.Service;

@Service
public class InformeService {

    private final IngresoRepository ingresoRepository;
    private final GastoRepository gastoRepository;

    public InformeService(IngresoRepository ingresoRepository,
                          GastoRepository gastoRepository) {
        this.ingresoRepository = ingresoRepository;
        this.gastoRepository = gastoRepository;
    }

    public double totalIngresos() {
        return ingresoRepository.totalIngresos();
    }

    public double totalGastos() {
        return gastoRepository.totalGastos();
    }

    public double balanceGeneral() {
        return totalIngresos() - totalGastos();
    }
}
