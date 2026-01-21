package com.crud.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.modelo.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Integer> {

}
