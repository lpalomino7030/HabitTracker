package com.cibertec.habittracker.repository;

import com.cibertec.habittracker.model.HabitoRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface HabitoRegistradoRepository extends JpaRepository<HabitoRegistrado, Long> {

    Optional<HabitoRegistrado> findByHabitoIdAndFecha(Long habitoId, LocalDate fecha);

    List<HabitoRegistrado> findByHabitoIdOrderByFechaDesc(Long habitoId);
}
