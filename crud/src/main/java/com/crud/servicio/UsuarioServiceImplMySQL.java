package com.crud.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crud.modelo.Usuario;
import com.crud.repositorio.UsuarioRepository;

@Service
public class UsuarioServiceImplMySQL implements UsuarioService {

	private UsuarioRepository repositorio;
	
	public UsuarioServiceImplMySQL(UsuarioRepository repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		return repositorio.findAll();
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		return repositorio.getReferenceById(id);
	}

	@Override
	public void agregarUsuario(Usuario usuario) {
		repositorio.save(usuario);
	}

	@Override
	public void actualizarUsuario(Usuario usuario) {
		if(repositorio.existsById(usuario.getId())) {
			repositorio.save(usuario);
		}
	}

	@Override
	public void eliminarUsuario(Integer id) {
		repositorio.deleteById(id);
	}

	public void guardarCIudades(List<Usuario> ciudades) {
		repositorio.saveAll(ciudades);
		
	}
}
