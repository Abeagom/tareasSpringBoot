package com.crud.controlador;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crud.modelo.Usuario;
import com.crud.servicio.UsuarioServiceImplMySQL;

@Controller
public class Controlador {

	private final UsuarioServiceImplMySQL usuarioServicio;
	
    public Controlador(UsuarioServiceImplMySQL usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    
    @GetMapping("/usuarios")
    public String listado(Model model) {
        List<Usuario> usuarios = usuarioServicio.obtenerUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "lista";
    }
}
