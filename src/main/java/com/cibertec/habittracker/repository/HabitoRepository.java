package com.cibertec.habittracker.repository;

import com.cibertec.habittracker.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {

    List<Habito> findByUsuarioNombreUsuario(String username);

}
