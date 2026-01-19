package servicio;

import java.util.List;

import com.crud.modelo.Ciudad;

public interface CiudadServicio {
	List<Ciudad> obtenerCiudades();
	Ciudad obtenerCiudadPorId(Integer id);
	void agregarCiudad(Ciudad ciudad);
	void actualizarCiudad(Ciudad ciudad);
	void eliminarCiudad(Integer id);
}
