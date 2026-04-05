package com.moneygest.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class InformeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Object[]> obtenerMovimientosUnificados(LocalDateTime inicio, LocalDateTime fin) {
        String sql = "SELECT fecha, 'INGRESO CAPITAL' as tipo, metodo as detalle, monto FROM ingresos_capital WHERE fecha BETWEEN :inicio AND :fin " +
                "UNION ALL " +
                "SELECT fecha, 'GASTO' as tipo, descripcion as detalle, monto FROM gastos WHERE fecha BETWEEN :inicio AND :fin " +
                "UNION ALL " +
                "SELECT fecha, 'VENTA' as tipo, numero_factura as detalle, total as monto FROM ventas WHERE fecha BETWEEN :inicio AND :fin " +
                "ORDER BY fecha DESC";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("inicio", inicio);
        query.setParameter("fin", fin);
        return query.getResultList();
    }

    public Double sumaTotalIngresosCapital() {
        Object result = entityManager.createNativeQuery("SELECT SUM(monto) FROM ingresos_capital").getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }

    public Double sumaTotalVentas() {
        Object result = entityManager.createNativeQuery("SELECT SUM(total) FROM ventas").getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }

    public Double sumaTotalGastos() {
        Object result = entityManager.createNativeQuery("SELECT SUM(monto) FROM gastos").getSingleResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }
}