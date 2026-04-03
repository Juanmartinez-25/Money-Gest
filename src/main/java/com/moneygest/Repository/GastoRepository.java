package com.moneygest.Repository;

import com.moneygest.Model.Gasto; // Corregido a Mayúscula
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    @Query("SELECT COALESCE(SUM(g.monto), 0) FROM Gasto g")
    double totalGastos();
}