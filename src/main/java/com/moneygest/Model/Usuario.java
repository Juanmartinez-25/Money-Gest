package com.moneygest.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Email
    private String correo;

    @NotBlank
    private String contrasena;

    @Column(name = "activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true; // Usamos Boolean con B mayúscula

    @Column(name = "solicitud_cambio_clave", columnDefinition = "TINYINT(1)")
    private Boolean solicitudCambioClave = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    private Rol rol;

    public Usuario() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // METODO QUE BUSCA EL SERVICE
    public Boolean isActivo() {
        return activo != null && activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = (activo != null) ? activo : true;
    }

    public Boolean isSolicitudCambioClave() {
        return solicitudCambioClave != null && solicitudCambioClave;
    }
    public void setSolicitudCambioClave(Boolean solicitudCambioClave) {
        this.solicitudCambioClave = (solicitudCambioClave != null) ? solicitudCambioClave : false;
    }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}