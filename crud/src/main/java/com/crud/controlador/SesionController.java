package com.crud.controlador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.crud.modelo.Sesion;
import com.crud.modelo.Usuario;
import com.crud.servicio.SesionServiceImplMySQL;
import com.crud.servicio.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sesiones")
public class SesionController {
	
    private SesionServiceImplMySQL sesionServicio;
    private final UsuarioService usuarioServicio;
    
    public SesionController(SesionServiceImplMySQL sesionServicio, UsuarioService usuarioServicio) {
        this.sesionServicio = sesionServicio;
        this.usuarioServicio = usuarioServicio;
      }

    // Listado de sesiones
	@GetMapping
	public String listarSesiones(Model model,
			@PageableDefault(size=20) Pageable page,
			@RequestParam(required=false) String motivo) {
		
		Page<Sesion>sesiones;
		if(motivo != null && !motivo.trim().isEmpty()) {
			sesiones = sesionServicio.buscarSesionesContienenMotivo(motivo, page);
		}else {
			sesiones = sesionServicio.listarTodasLasSesiones(page);
		}
		
		Usuario usuario = usuarioServicio.obtenerUsuarioConectado();
		
	    model.addAttribute("usuario", usuario);
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