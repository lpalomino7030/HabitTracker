package com.cibertec.habittracker.repository;

import com.cibertec.habittracker.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario);
    @Transactional
    @Modifying
    @Query(value = """ 
UPDATE usuario SET nombres=:nombres, where idusuario=:idusuario 
""", nativeQuery = true)
    void actualizarUsuario(@Param("nombres") String nombre ,
                           @Param("idusuario") Integer idusuario); }