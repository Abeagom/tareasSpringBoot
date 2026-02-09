package com.crud.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	  Usuario findByNombre(String username);
	  Page<Usuario> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
	}
