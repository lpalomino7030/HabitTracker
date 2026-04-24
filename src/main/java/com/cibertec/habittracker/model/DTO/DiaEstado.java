package com.cibertec.habittracker.model.DTO;

public class DiaEstado {
    private String dia;
    private boolean completado;

    public DiaEstado(String dia, boolean completado) {
        this.dia = dia;
        this.completado = completado;
    }

    public String getDia() { return dia; }
    public boolean isCompletado() { return completado; }

}
