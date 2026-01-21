package com.crud.configuracion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crud.modelo.Sesion;
import com.crud.repositorio.SesionRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class InicializadorDatos {

    @Bean
    CommandLineRunner inicializarBaseDeDatos(SesionRepository repository) {
        return args -> {
            // Solo cargamos si la tabla está vacía
            if (repository.count() == 0) {
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

                            repository.save(s);
                        }
                    }
                    System.out.println("¡Importación completada! Sesiones guardadas: " + repository.count());
                    
                } catch (Exception e) {
                    System.err.println("Error al cargar sesiones: " + e.getMessage());
                }
            } else {
                System.out.println("La tabla de sesiones ya tiene datos, no es necesario importar.");
            }
        };
    }
}