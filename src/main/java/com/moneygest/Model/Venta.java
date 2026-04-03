package com.moneygest.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

    private String numero_factura;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private String metodo_pago;
    private Double subtotal;
    private Double iva;
    private Double total;
    private Integer id_usuario;

    public Venta() {}

    // Getters y Setters
    public Integer getId_venta() { return id_venta; }
    public void setId_venta(Integer id_venta) { this.id_venta = id_venta; }
    public String getNumero_factura() { return numero_factura; }
    public void setNumero_factura(String numero_factura) { this.numero_factura = numero_factura; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getMetodo_pago() { return metodo_pago; }
    public void setMetodo_pago(String metodo_pago) { this.metodo_pago = metodo_pago; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public Double getIva() { return iva; }
    public void setIva(Double iva) { this.iva = iva; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Integer getId_usuario() { return id_usuario; }
    public void setId_usuario(Integer id_usuario) { this.id_usuario = id_usuario; }
}