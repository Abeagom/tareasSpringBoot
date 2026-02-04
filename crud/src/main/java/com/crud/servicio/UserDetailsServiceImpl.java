package com.crud.servicio;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.crud.modelo.Usuario;
import com.crud.repositorio.UsuarioRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsuarioRepository usuarioRepositorio;

  public UserDetailsServiceImpl(UsuarioRepository usuarioRepositorio) {
    this.usuarioRepositorio = usuarioRepositorio;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Usuario usuario = usuarioRepositorio.findByNombre(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }

    return User.withUsername(usuario.getNombre())
      .password(usuario.getContrasena())       // ya viene encriptada en BBDD
      .roles(usuario.getRol().toString())      // crea ROLE_XXX internamente
      .build();
  }
}
