package com.cibertec.habittracker.service;

import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.repository.HabitoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitoService {

    private final HabitoRepository habitoRepository;

    public HabitoService(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    public List<Habito> listar() {
        return habitoRepository.findAll();
    }

    public Habito guardar(Habito habito)  {
        habito.setFechaCreacion(LocalDate.now());
        return habitoRepository.save(habito);
    }

    public List<Integer> diasDelMes() {
        LocalDate hoy = LocalDate.now();
        int diasEnMes = hoy.lengthOfMonth();

        List<Integer> dias = new ArrayList<>();

        for (int i = 1; i <= diasEnMes; i++) {
            dias.add(i);
        }

        return dias;
    }

    public Habito obtenerPorId(Long id) {
        return habitoRepository.findById(id).orElseThrow();
    }

    public Integer contarHabitos() {
        return (int) habitoRepository.count();
    }

    public void eliminar(Long id) {
        habitoRepository.deleteById(id);
    }
}
