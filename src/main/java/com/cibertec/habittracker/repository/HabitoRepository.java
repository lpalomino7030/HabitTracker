package com.cibertec.habittracker.repository;

import com.cibertec.habittracker.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
}
