package com.cibertec.habittracker.service;


import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.model.HabitoRegistrado;
import com.cibertec.habittracker.repository.HabitoRegistradoRepository;
import com.cibertec.habittracker.repository.HabitoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HabitoRegistradoService {

    private final HabitoRegistradoRepository registroRepository;
    private final HabitoRepository habitoRepository;

    public HabitoRegistradoService(HabitoRegistradoRepository registroRepository, HabitoRepository habitoRepository) {
        this.registroRepository = registroRepository;
        this.habitoRepository = habitoRepository;
    }

    public void marcarHoy(Long habitoId){
        LocalDate hoy = LocalDate.now();
        Optional<HabitoRegistrado> existente = registroRepository.findByHabitoIdAndFecha(habitoId, hoy);

        if(existente.isPresent()){
            HabitoRegistrado log = existente.get();
            log.setCompletado(!log.getCompletado());
            registroRepository.save(log);
        } else {
            Habito habito = habitoRepository.findById(habitoId).orElseThrow();

            HabitoRegistrado nuevo = new HabitoRegistrado();
            nuevo.setFecha(hoy);
            nuevo.setCompletado(true);
            nuevo.setHabito(habito);

            registroRepository.save(nuevo);
        }

    }

    public int calcularLaRacha(Long habitoId){
        List<HabitoRegistrado> logs = registroRepository.findByHabitoIdOrderByFechaDesc(habitoId);

        int racha = 0;
        LocalDate fecha = LocalDate.now();

        for(HabitoRegistrado log : logs){
            if (log.getFecha().equals(fecha) && log.getCompletado()){
                racha++;

                fecha = fecha.minusDays(1);
            } else {
                break;
            }
        }
        return racha;
    }

}
