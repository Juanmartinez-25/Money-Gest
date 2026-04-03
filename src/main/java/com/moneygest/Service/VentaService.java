package com.moneygest.Service;

import com.moneygest.Model.Venta;
import com.moneygest.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public void guardarVenta(Venta venta) {
        ventaRepository.save(venta);
    }

    public void eliminarVenta(Integer id) {
        ventaRepository.deleteById(id);
    }

    // Calcula el total sumando el campo 'total' de tu modelo
    public double totalVentas() {
        return ventaRepository.findAll()
                .stream()
                .mapToDouble(Venta::getTotal) // Usamos getTotal directamente
                .sum();
    }
}