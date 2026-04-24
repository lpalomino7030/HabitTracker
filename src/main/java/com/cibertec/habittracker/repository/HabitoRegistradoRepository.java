package com.cibertec.habittracker.repository;

import com.cibertec.habittracker.model.HabitoRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface HabitoRegistradoRepository extends JpaRepository<HabitoRegistrado, Long> {

    Optional<HabitoRegistrado> findByHabitoIdAndFecha(Long habitoId, LocalDate fecha);

    List<HabitoRegistrado> findByHabitoIdOrderByFechaDesc(Long habitoId);
    List<HabitoRegistrado> findByHabitoIdAndFechaBetween(
            Long habitoId,
            LocalDate inicio,
            LocalDate fin
    );

    @Query("""
SELECT COUNT(r)
FROM HabitoRegistrado r
WHERE r.habito.id = :habitoId
AND r.completado = true
AND r.fecha >= :fechaInicio
""")
    int contarCompletadosSemana(Long habitoId, LocalDate fechaInicio);
}
