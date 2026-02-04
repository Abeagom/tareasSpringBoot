package com.crud.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.crud.modelo.Rol;
import com.crud.modelo.Sesion;
import com.crud.modelo.Usuario;
import com.crud.repositorio.SesionRepository;
import com.crud.repositorio.UsuarioRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class InicializadorDatos implements CommandLineRunner {
	
	  private final SesionRepository sesionRepositorio;
	  private final UsuarioRepository usuarioRepositorio;
	  private final PasswordEncoder passwordEncoder;
	  
		public InicializadorDatos(SesionRepository sesionRepositorio, UsuarioRepository usuarioRepositorio,
				PasswordEncoder passwordEncoder) {
			this.sesionRepositorio = sesionRepositorio;
			this.usuarioRepositorio = usuarioRepositorio;
			this.passwordEncoder = passwordEncoder;
		}
		
		  private void crearUsuarioSiNoExiste(String nombre, String contrasenaEnClaro, Rol rol) {
			    if (usuarioRepositorio.findByNombre(nombre) != null) return;

			    Usuario u = new Usuario();
			    u.setNombre(nombre);
			    u.setContrasena(passwordEncoder.encode(contrasenaEnClaro));
			    u.setRol(rol);

			    usuarioRepositorio.save(u);
			  }

    @Override
    public void run(String... args) throws Exception {
        // Solo cargamos si la tabla está vacía
        if (sesionRepositorio.count() == 0) {
            System.out.println("Iniciando importación de sesiones desde CSV");
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/sesiones.csv"), StandardCharsets.UTF_8))) {

                DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String linea;
                br.readLine(); // Saltar la primera línea (cabecera del CSV)

                while ((linea = br.readLine()) != null) {
                    // Usamos el punto y coma (;) como separador
                    String[] datos = linea.split(";"); 
                    
                    if (datos.length >= 5) { 
                        Sesion s = new Sesion();
                        
                        s.setFechaHora(LocalDateTime.parse(datos[0].trim(), formateador));
                        s.setModalidad(datos[1].trim());
                        s.setEstado(datos[2].trim());
                        s.setDuracion(Integer.parseInt(datos[3].trim()));
                        s.setMotivo(datos[4].trim());

                        sesionRepositorio.save(s);
                    }
                }
                System.out.println("¡Importación completada! Sesiones guardadas: " + sesionRepositorio.count());
                
            } catch (Exception e) {
                System.err.println("Error al cargar sesiones: " + e.getMessage());
            }
        } else {
            System.out.println("La tabla de sesiones ya tiene datos, no es necesario importar.");
        }
        
        crearUsuarioSiNoExiste("admin", "admin123", Rol.ADMIN);
        crearUsuarioSiNoExiste("manager", "manager123", Rol.MANAGER);
        crearUsuarioSiNoExiste("usuario", "usuario123", Rol.USUARIO);
    }
}