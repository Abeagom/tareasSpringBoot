package com.crud.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.crud.modelo.Sesion;
import com.crud.servicio.SesionService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/sesiones")
public class SesionController {
	
    @Autowired
    private SesionService sesionServicio;

    // Listado de sesiones
    @GetMapping
    public String listarSesiones(Model model) {
        model.addAttribute("sesiones", sesionServicio.listarSesiones());
        return "sesiones/lista";
    }

    // Crear sesión
    @GetMapping("/nuevo")
    public String mostrarFormularioDeCrear(Model model) {
        model.addAttribute("sesion", new Sesion());
        return "sesiones/form";
    }

    @PostMapping
    public String guardarSesion(@Valid @ModelAttribute("sesion") Sesion sesion, BindingResult result) {
        if (result.hasErrors()) {
            return "sesiones/form";
        }
        sesionServicio.guardar(sesion);
        return "redirect:/sesiones";
    }

    // Editar sesión
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditar(@PathVariable Integer id, Model model) {
        model.addAttribute("sesion", sesionServicio.obtenerPorId(id));
        return "sesiones/form";
    }

    // Actualizar sesión
    @PostMapping("/{id}")
    public String actualizarSesion(@PathVariable Integer id, @Valid @ModelAttribute("sesion") Sesion sesion, BindingResult result) {
        if (result.hasErrors()) {
            return "sesiones/form";
        }
        sesion.setId(id);
        sesionServicio.actualizar(sesion);
        return "redirect:/sesiones";
    }

    // Eliminar sesión
    @PostMapping("/{id}/borrar")
    public String eliminarSesion(@PathVariable Integer id) {
        sesionServicio.eliminar(id);
        return "redirect:/sesiones";
    }

    // Ver detalles sesión
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Sesion sesion = sesionServicio.obtenerPorId(id);
        if (sesion == null) {
            return "redirect:/sesiones";
        }
        model.addAttribute("sesion", sesion);
        return "sesiones/detalle";
    }
}