package com.crud.configuracion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crud.modelo.Ciudad;
import com.github.javafaker.Faker;

import servicio.CiudadServicioImplMySQL;

@Component
public class InicializadorDatos implements CommandLineRunner  {
	 @Autowired
	    private CiudadServicioImplMySQL ciudadRepository;

	@Override
	public void run(String... args) throws Exception {

		 Faker faker = new Faker();
	        List<Ciudad> ciudades = new ArrayList<>();

	        // Crear 10 ciudades
	        for (int i = 0; i < 10; i++) {
	            Ciudad ciudad = new Ciudad();
	            ciudad.setNombre(faker.name().fullName());
	            ciudades.add(ciudad);
	        }
	}

}

