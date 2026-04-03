package com.moneygest.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Integer id; // Cambiado a Integer

    @Column(name = "numero_factura")
    private String numeroFactura;

    private LocalDate fecha;

    @Column(name = "metodo_pago")
    private String metodoPago;

    private double total;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    /**
     * IMPORTANTE: Este método soluciona el error "cannot find symbol: method getMonto(T)"
     * Mapea 'total' como 'monto' para compatibilidad con cálculos genéricos.
     */
    public double getMonto() {
        return this.total;
    }
}