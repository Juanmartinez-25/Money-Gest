package com.moneygest.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gastos")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Integer id;

    private LocalDateTime fecha;
    private String descripcion;
    private Double monto;
    private String estado;
    private String subcategoria;

    @Column(name = "unidad_negocio")
    private String unidadNegocio;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getSubcategoria() { return subcategoria; }
    public void setSubcategoria(String subcategoria) { this.subcategoria = subcategoria; }
    public String getUnidadNegocio() { return unidadNegocio; }
    public void setUnidadNegocio(String unidadNegocio) { this.unidadNegocio = unidadNegocio; }
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
}