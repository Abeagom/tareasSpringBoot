package com.crud.controlador;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.crud.modelo.Ciudad;

import servicio.CiudadServicioImplMySQL;

@Controller
public class Controlador {

	private final CiudadServicioImplMySQL ciudadServicio;
	
    public Controlador(CiudadServicioImplMySQL ciudadServicio) {
        this.ciudadServicio = ciudadServicio;
    }
    
    @GetMapping("/ciudades")
    public String listado(Model model) {
        List<Ciudad> ciudades = ciudadServicio.obtenerCiudades();
        model.addAttribute("listaCiudades", ciudades);
        return "lista";
    }
}
