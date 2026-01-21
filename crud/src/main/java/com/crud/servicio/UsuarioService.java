package com.crud.servicio;

import java.util.List;

import com.crud.modelo.Usuario;

public interface UsuarioService {
	List<Usuario> obtenerUsuarios();
	Usuario obtenerUsuarioPorId(Integer id);
	void agregarUsuario(Usuario usuario);
	void actualizarUsuario(Usuario ciudad);
	void eliminarUsuario(Integer id);
}
