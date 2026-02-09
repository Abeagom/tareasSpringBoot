package com.crud.controlador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.modelo.Sesion;
import com.crud.servicio.SesionServiceImplMySQL;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sesiones")
public class SesionController {
    
    private final SesionServiceImplMySQL sesionServicio;
    
    public SesionController(SesionServiceImplMySQL sesionServicio) {
        this.sesionServicio = sesionServicio;
    }

    // Listado de sesiones
    @GetMapping
    public String listarSesiones(Model model,
                                 @PageableDefault(size=20) Pageable page,
                                 @RequestParam(required=false) String motivo) {
        
        Page<Sesion> sesiones;
        if(motivo != null && !motivo.trim().isEmpty()) {
            sesiones = sesionServicio.buscarSesionesContienenMotivo(motivo, page);
        } else {
            sesiones = sesionServicio.listarTodasLasSesiones(page);
        }
        
        model.addAttribute("sesiones", sesiones);
        model.addAttribute("total", sesiones.getTotalElements());
        model.addAttribute("motivoBusqueda", motivo);
        return "sesiones/lista";
    }

    // Crear sesión
    @GetMapping("/nuevo")
    public String mostrarFormularioDeCrear(Model model) {
        model.addAttribute("sesion", new Sesion());
        return "sesiones/form";
    }

    @PostMapping
    public String guardarSesion(@Valid @ModelAttribute("sesion") Sesion sesion,
                                BindingResult result,
                                RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "sesiones/form";
        }
        sesionServicio.guardar(sesion);
        redirectAttrs.addFlashAttribute("exito", "Sesión creada correctamente");
        return "redirect:/sesiones";
    }

    // Editar sesión
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditar(@PathVariable Integer id, Model model,
                                            RedirectAttributes redirectAttrs) {
        Sesion sesion = sesionServicio.obtenerPorId(id);
        if (sesion == null) {
            redirectAttrs.addFlashAttribute("error", "Sesión no encontrada");
            return "redirect:/sesiones";
        }
        model.addAttribute("sesion", sesion);
        return "sesiones/form";
    }

    // Actualizar sesión
    @PostMapping("/{id}")
    public String actualizarSesion(@PathVariable Integer id,
                                   @Valid @ModelAttribute("sesion") Sesion sesion,
                                   BindingResult result,
                                   RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "sesiones/form";
        }
        sesion.setId(id);
        sesionServicio.actualizar(sesion);
        redirectAttrs.addFlashAttribute("exito", "Sesión actualizada correctamente");
        return "redirect:/sesiones";
    }

    // Eliminar sesión
    @PostMapping("/{id}/borrar")
    public String eliminarSesion(@PathVariable Integer id,
                                 RedirectAttributes redirectAttrs) {
        sesionServicio.eliminar(id);
        redirectAttrs.addFlashAttribute("exito", "Sesión eliminada correctamente");
        return "redirect:/sesiones";
    }

    // Ver detalles sesión
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Integer id, Model model, RedirectAttributes redirectAttrs) {
        Sesion sesion = sesionServicio.obtenerPorId(id);
        if (sesion == null) {
            redirectAttrs.addFlashAttribute("error", "Sesión no encontrada");
            return "redirect:/sesiones";
        }
        model.addAttribute("sesion", sesion);
        return "sesiones/detalle";
    }
}
