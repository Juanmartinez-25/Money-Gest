package com.moneygest.Repository;

import com.moneygest.Model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {

    @Query("SELECT COALESCE(SUM(i.monto), 0) FROM Ingreso i")
    double totalIngresos();

}
