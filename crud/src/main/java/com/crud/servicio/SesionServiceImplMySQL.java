package com.crud.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.crud.modelo.Sesion;
import com.crud.repositorio.SesionRepository;

@Service
public class SesionServiceImplMySQL implements SesionService {

    private SesionRepository repositorio;
    
    public SesionServiceImplMySQL(SesionRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Sesion> listarSesiones() {
        return repositorio.findAll();
    }

    @Override
    public Sesion obtenerPorId(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public void guardar(Sesion sesion) {
        repositorio.save(sesion);
    }

    @Override
    public void actualizar(Sesion sesion) {
        if (sesion.getId() != null && repositorio.existsById(sesion.getId())) {
            repositorio.save(sesion);
        }
    }

    @Override
    public void eliminar(Integer id) {
        repositorio.deleteById(id);
    }

    public void guardarVarias(List<Sesion> sesiones) {
        repositorio.saveAll(sesiones);
    }
    
	public Page<Sesion> listarTodasLasSesiones(Pageable pageable ){
		return repositorio.findAll(pageable);
	}
	
	public Page<Sesion> buscarSesionesContienenMotivo (String motivo, Pageable pageable ){
		return repositorio.findByMotivoContaining(motivo, pageable);
	}
}