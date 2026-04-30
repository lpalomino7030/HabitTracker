package com.cibertec.habittracker.service;


import com.cibertec.habittracker.model.DTO.DiaEstado;
import com.cibertec.habittracker.model.Habito;
import com.cibertec.habittracker.model.HabitoRegistrado;
import com.cibertec.habittracker.repository.HabitoRegistradoRepository;
import com.cibertec.habittracker.repository.HabitoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitoRegistradoService {

    private final HabitoRegistradoRepository registroRepository;
    private final HabitoRepository habitoRepository;

    public HabitoRegistradoService(HabitoRegistradoRepository registroRepository, HabitoRepository habitoRepository) {
        this.registroRepository = registroRepository;
        this.habitoRepository = habitoRepository;
    }

    public void marcarHoy(Long habitoId, String username){

        Habito habito = habitoRepository
                .findById(habitoId)
                .orElseThrow();

        if (!habito.getUsuario().getNombreUsuario().equals(username)) {
            throw new RuntimeException("Acceso no autorizado");
        }

        LocalDate hoy = LocalDate.now();

        Optional<HabitoRegistrado> existente =
                registroRepository.findByHabitoIdAndFecha(habitoId, hoy);

        if(existente.isPresent()){
            HabitoRegistrado log = existente.get();
            log.setCompletado(true);
            registroRepository.save(log);
        } else {
            HabitoRegistrado nuevo = new HabitoRegistrado();
            nuevo.setHabito(habito);
            nuevo.setFecha(hoy);
            nuevo.setCompletado(true);

            registroRepository.save(nuevo);
        }
    }

    public List<DiaEstado> obtenerSemana(Long habitoId) {

        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusDays(6);

        List<HabitoRegistrado> registros =
                registroRepository.findByHabitoIdAndFechaBetween(habitoId, inicio, hoy);

        Map<LocalDate, Boolean> mapa = registros.stream()
                .collect(Collectors.toMap(
                        HabitoRegistrado::getFecha,
                        HabitoRegistrado::getCompletado
                ));

        List<DiaEstado> resultado = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);

            boolean estado = mapa.getOrDefault(fecha, false);

            String dia = switch (fecha.getDayOfWeek()) {
                case MONDAY -> "L";
                case TUESDAY -> "M";
                case WEDNESDAY -> "X";
                case THURSDAY -> "J";
                case FRIDAY -> "V";
                case SATURDAY -> "S";
                case SUNDAY -> "D";
            };

            resultado.add(new DiaEstado(dia, estado));
        }

        return resultado;
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
    public int mejorRacha(String username) {

        List<Habito> habitos =
                habitoRepository.findByUsuarioNombreUsuario(username);

        int mayorRacha = 0;

        for (Habito h : habitos) {
            int racha = calcularLaRacha(h.getId());

            if (racha > mayorRacha) {
                mayorRacha = racha;
            }
        }

        return mayorRacha;
    }
    public int obtenerMayorRachaActual(String username) {
        List<Habito> habitos = habitoRepository.findByUsuarioNombreUsuario(username);

        int mayor = 0;

        for (Habito h : habitos) {
            int racha = calcularLaRacha(h.getId());

            if (racha > mayor) {
                mayor = racha;
            }
        }

        return mayor;
    }

    public List<Integer> obtenerDiasMarcadosDelMes(Long habitoId) {

        LocalDate hoy = LocalDate.now();

        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

        List<HabitoRegistrado> registros =
                registroRepository.findByHabitoIdAndFechaBetween(habitoId, inicioMes, finMes);

        List<Integer> dias = new ArrayList<>();

        for (HabitoRegistrado r : registros) {
            if (r.getCompletado()) {
                dias.add(r.getFecha().getDayOfMonth());
            }
        }

        return dias;
    }

    public double porcentajeSemanal(Long habitoId) {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusDays(6);

        int completados = registroRepository.contarCompletadosSemana(habitoId, inicioSemana);

        return (completados / 7.0) * 100;
    }

}
