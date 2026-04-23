package com.cibertec.habittracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "habito")
public class Habito {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private String nombre;
private String descripcion;
private LocalDate fechaCreacion;
    @OneToMany(mappedBy = "habito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HabitoRegistrado> registros;

    public Habito() {
    }

    public Habito(Long id, String nombre, String descripcion, LocalDate fechaCreacion, List<HabitoRegistrado> registros) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.registros = registros;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<HabitoRegistrado> getRegistros() {
        return registros;
    }

    public void setRegistros(List<HabitoRegistrado> registros) {
        this.registros = registros;
    }
}
