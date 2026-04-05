package com.moneygest.Service;

import com.moneygest.Model.Producto;
import com.moneygest.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto guardarOActualizar(Producto p) {
        return productoRepository.findByCodigo(p.getCodigo())
                .map(existente -> {

                    existente.setStock(existente.getStock() + p.getStock());
                    existente.setPrecioCompra(p.getPrecioCompra());
                    existente.setPrecioVenta(p.getPrecioVenta());
                    return productoRepository.save(existente);
                })
                .orElseGet(() -> productoRepository.save(p));
    }
}