package com.crud.servicio;

import java.util.List;
import com.crud.modelo.Sesion;

public interface SesionService {
    
    // Obtener todas las sesiones
    List<Sesion> listarSesiones();
    
    // Buscar una sesión por su ID
    Sesion obtenerPorId(Integer id);
    
    // Guardar una nueva sesión
    void guardar(Sesion sesion);
    
    // Actualizar una sesión existente
    void actualizar(Sesion sesion);
    
    // Eliminar una sesión por su ID
    void eliminar(Integer id);
}