package com.crud.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean //Acceso a AuthenticationManager
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
		throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}

	
}
