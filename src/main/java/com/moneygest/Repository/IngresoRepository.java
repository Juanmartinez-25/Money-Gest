package com.moneygest.Repository;

import com.moneygest.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    @Query("SELECT COALESCE(SUM(i.monto), 0) FROM Ingreso i")
    double totalIngresos();
}
