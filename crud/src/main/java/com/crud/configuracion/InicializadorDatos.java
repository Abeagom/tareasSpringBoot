package com.crud.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crud.modelo.Usuario;
import com.crud.repositorio.UsuarioRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Configuration
public class InicializadorDatos {

    @Bean
    CommandLineRunner inicializarBaseDeDatos(UsuarioRepository repository) {
        return args -> {
            // Solo cargamos si la tabla está vacía
            if (repository.count() == 0) {
                System.out.println("Iniciando importación de usuarios desde CSV...");
                
                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        getClass().getResourceAsStream("/usuarios.csv"), StandardCharsets.UTF_8))) {
                    
                    String linea;
                    br.readLine(); // Saltar la primera línea (cabecera del CSV)

                    while ((linea = br.readLine()) != null) {
                        // Usamos el punto y coma (;) como separador
                        String[] datos = linea.split(";"); 
                        
                        if (datos.length >= 6) {
                            Usuario u = new Usuario();
                            u.setNombre(datos[0].trim());
                            u.setApellidos(datos[1].trim());
                            u.setEmail(datos[2].trim());
                            u.setContrasena(datos[3].trim());
                            u.setTelefono(datos[4].trim());
                            u.setFechaNacimiento(LocalDate.parse(datos[5].trim()));

                            repository.save(u);
                        }
                    }
                    System.out.println("¡Importación completada! Usuarios guardados: " + repository.count());
                    
                } catch (Exception e) {
                    System.err.println("Error al cargar usuarios: " + e.getMessage());
                }
            } else {
                System.out.println("La tabla de usuarios ya tiene datos, no es necesario importar.");
            }
        };
    }
}