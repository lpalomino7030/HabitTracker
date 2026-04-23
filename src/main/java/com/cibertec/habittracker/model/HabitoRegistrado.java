package com.cibertec.habittracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(
        name = "habito_Registrado",
        uniqueConstraints = @UniqueConstraint(columnNames = {"habito_id", "fecha"})
)
public class HabitoRegistrado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private Boolean completado;
    @ManyToOne
    @JoinColumn(name = "habito_id", nullable = false)
    private Habito habito;

    public HabitoRegistrado() {
    }

    public HabitoRegistrado(Long id, LocalDate fecha, Boolean completado, Habito habito) {
        this.id = id;
        this.fecha = fecha;
        this.completado = completado;
        this.habito = habito;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public Habito getHabito() {
        return habito;
    }

    public void setHabito(Habito habito) {
        this.habito = habito;
    }
}
