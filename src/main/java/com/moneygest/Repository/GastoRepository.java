package com.moneygest.Repository;

import com.moneygest.Model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Integer> {

    // Método para calcular el total de gastos (útil para el saldo disponible)
    @Query("SELECT COALESCE(SUM(g.monto), 0) FROM Gasto g")
    Double totalGastos();

}