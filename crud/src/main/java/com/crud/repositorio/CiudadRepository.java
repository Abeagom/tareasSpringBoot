package com.crud.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.modelo.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

}
