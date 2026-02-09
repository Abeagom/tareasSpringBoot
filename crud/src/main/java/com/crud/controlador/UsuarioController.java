package com.crud.controlador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud.modelo.Usuario;
import com.crud.servicio.UsuarioServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServicio;

    public UsuarioController(UsuarioServiceImpl usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // Listado de usuarios
    @GetMapping
    public String listarUsuarios(Model model,
                                 @PageableDefault(size=20) Pageable page,
                                 @RequestParam(required=false) String nombre) {
        
        Page<Usuario> usuarios;
        if(nombre != null && !nombre.trim().isEmpty()) {
            usuarios = usuarioServicio.buscarPorNombre(nombre, page);
        } else {
            usuarios = usuarioServicio.listar(page);
        }
        
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("total", usuarios.getTotalElements());
        model.addAttribute("nombreBusqueda", nombre);
        return "usuarios/lista";
    }

    // Crear usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioDeCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                 BindingResult result,
                                 RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "usuarios/form";
        }
        usuarioServicio.crear(
                usuario.getNombre(),
                usuario.getContrasena(),
                usuario.getRol()
            );
        redirectAttrs.addFlashAttribute("exito", "Usuario creado correctamente");
        return "redirect:/usuarios";
    }

 // Editar usuario
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditar(@PathVariable Long id,
                                            Model model,
                                            RedirectAttributes redirectAttrs) {
        Usuario usuario = usuarioServicio.obtenerPorId(id);
        
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";
        }

        // ADMIN protegido
        if (usuario.getRol().toString().contains("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "No se permite editar a un administrador.");
            return "redirect:/usuarios";
        }
        // ---------------------

        model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }

    // Actualizar usuario
    @PostMapping("/{id}")
    public String actualizarUsuario(@PathVariable Long id,
                                    @Valid @ModelAttribute("usuario") Usuario usuario,
                                    BindingResult result,
                                    RedirectAttributes redirectAttrs) {
        
    	// ADMIN protegido
        Usuario usuarioExistente = usuarioServicio.obtenerPorId(id);
        if (usuarioExistente != null && usuarioExistente.getRol().toString().contains("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "Acción no permitida para administradores.");
            return "redirect:/usuarios";
        }
        // --------------------------------------------

        if (result.hasErrors()) {
            return "usuarios/form";
        }
        usuario.setId(id);
        usuarioServicio.actualizar(
                id,
                usuario.getNombre(),
                usuario.getRol()
            );
        redirectAttrs.addFlashAttribute("exito", "Usuario actualizado correctamente");
        return "redirect:/usuarios";
    }

    // Eliminar usuario
    @PostMapping("/{id}/borrar")
    public String eliminarUsuario(@PathVariable Long id,
                                  RedirectAttributes redirectAttrs) {
        
    	// ADMIN protegido
        Usuario usuario = usuarioServicio.obtenerPorId(id);
        if (usuario != null && usuario.getRol().toString().contains("ADMIN")) {
            redirectAttrs.addFlashAttribute("error", "No se puede eliminar a un administrador del sistema.");
            return "redirect:/usuarios";
        }

        usuarioServicio.eliminar(id);
        redirectAttrs.addFlashAttribute("exito", "Usuario eliminado correctamente");
        return "redirect:/usuarios";
    }

    // Ver detalles usuario
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        Usuario usuario = usuarioServicio.obtenerPorId(id);
        if (usuario == null) {
            redirectAttrs.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/detalle";
    }
}
