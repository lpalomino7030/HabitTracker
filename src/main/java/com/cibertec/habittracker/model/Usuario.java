package com.cibertec.habittracker.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;
    private String email;

    @Column(name = "nombre_usuario", unique = true)
    private String nombreUsuario;

    private String contrasena;

    @OneToMany(mappedBy = "habito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habito> habitos;

    public Usuario() {}

    public Usuario(Long id, String nombres, String nombreUsuario, String contrasena, List<Habito> habitos) {
        this.id = id;
        this.nombres = nombres;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.habitos = habitos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(List<Habito> habitos) {
        this.habitos = habitos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
