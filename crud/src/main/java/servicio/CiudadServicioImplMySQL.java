package servicio;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.crud.modelo.Ciudad;
import com.crud.repositorio.CiudadRepository;

@Service
public class CiudadServicioImplMySQL implements CiudadServicio {

	private CiudadRepository repositorio;
	
	public CiudadServicioImplMySQL(CiudadRepository repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public List<Ciudad> obtenerCiudades() {
		return repositorio.findAll();
	}

	@Override
	public Ciudad obtenerCiudadPorId(Integer id) {
		return repositorio.getReferenceById(id);
	}

	@Override
	public void agregarCiudad(Ciudad ciudad) {
		repositorio.save(ciudad);
	}

	@Override
	public void actualizarCiudad(Ciudad ciudad) {
		if(repositorio.existsById(ciudad.getId())) {
			repositorio.save(ciudad);
		}
	}

	@Override
	public void eliminarCiudad(Integer id) {
		repositorio.deleteById(id);
	}

	public void guardarCIudades(List<Ciudad> ciudades) {
		repositorio.saveAll(ciudades);
		
	}
}
