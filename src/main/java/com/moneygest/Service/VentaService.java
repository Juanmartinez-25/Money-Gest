package com.moneygest.Service;

import com.moneygest.Model.Venta;
import com.moneygest.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepo;

    public void guardarVentaSimple(Venta venta) {
        ventaRepo.save(venta);
    }
}