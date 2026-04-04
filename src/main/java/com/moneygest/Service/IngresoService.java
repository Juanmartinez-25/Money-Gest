
package com.moneygest.Service;

import com.moneygest.Model.Ingreso;
import com.moneygest.Repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    public Ingreso guardarIngreso(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    public List<Ingreso> listarIngresos() {
        return ingresoRepository.findAll();
    }

    public double obtenerTotalIngresos() {
        return ingresoRepository.totalIngresos();
    }

}