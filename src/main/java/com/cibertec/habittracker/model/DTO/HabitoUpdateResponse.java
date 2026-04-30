package com.cibertec.habittracker.model.DTO;

import java.util.List;

public class HabitoUpdateResponse {
    private List<DiaEstado> semana;
    private List<Integer> diasMarcados;
    private double porcentaje;

    public HabitoUpdateResponse() {
    }

    public HabitoUpdateResponse(List<DiaEstado> semana, List<Integer> diasMarcados, double porcentaje) {
        this.semana = semana;
        this.diasMarcados = diasMarcados;
        this.porcentaje = porcentaje;
    }

    public List<DiaEstado> getSemana() {
        return semana;
    }

    public void setSemana(List<DiaEstado> semana) {
        this.semana = semana;
    }

    public List<Integer> getDiasMarcados() {
        return diasMarcados;
    }

    public void setDiasMarcados(List<Integer> diasMarcados) {
        this.diasMarcados = diasMarcados;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
