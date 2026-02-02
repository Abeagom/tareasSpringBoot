package com.crud.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.modelo.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {
    // Método para buscar estudiantes por un criterio específico, por ejemplo, nombre
    List<Sesion> findByMotivo(String motivo);
    
    Page<Sesion> findAll(Pageable pageable);
    //Page<Sesion> findByNombre(String nombre, Pageable pageable);
    Page<Sesion> findByMotivoContaining(String motivo, Pageable pageable);
}
