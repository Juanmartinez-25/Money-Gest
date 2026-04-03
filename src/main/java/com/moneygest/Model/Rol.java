package com.moneygest.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol") // Mantenemos el nombre de columna que definimos en MySQL
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    // Constructor vacío obligatorio para JPA
    public Rol() {}

    // --- GETTERS Y SETTERS (Indispensables para que el login funcione) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}