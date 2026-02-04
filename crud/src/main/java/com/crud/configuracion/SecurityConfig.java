package com.crud.configuracion;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean // Acceso a AuthenticationManager
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean // Encriptar contraseñas
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	UserDetailsService users(PasswordEncoder passwordEncoder) {
		UserDetails user1 = User.builder().username("user1").password(passwordEncoder.encode("user1")).roles("USER")
				.build();
		UserDetails admin1 = User.builder().username("admin1").password(passwordEncoder.encode("admin1")).roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user1, admin1);
	}
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                // rutas públicas
                .requestMatchers("/").permitAll()

                // LISTAR sesiones → GET /sesiones
                .requestMatchers(HttpMethod.GET, "/sesiones").hasAnyRole("USER", "ADMIN")
                // VER detalle sesión → GET /sesiones/{id}
                .requestMatchers(HttpMethod.GET, "/sesiones/*").hasAnyRole("USER", "ADMIN")

                // CREAR sesión → GET /sesiones/nuevo y POST /sesiones
                .requestMatchers(HttpMethod.GET, "/sesiones/nuevo").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/sesiones").hasRole("ADMIN")

                // EDITAR sesión → GET /sesiones/{id}/editar y POST /sesiones/{id}
                .requestMatchers(HttpMethod.GET, "/sesiones/*/editar").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/sesiones/*").hasRole("ADMIN")

                // BORRAR sesión → POST /sesiones/{id}/borrar
                .requestMatchers(HttpMethod.POST, "/sesiones/*/borrar").hasRole("ADMIN")

                // cualquier otra ruta requiere login
                .anyRequest().authenticated()
        );

        // login form por defecto
        http.formLogin(form -> form
                .defaultSuccessUrl("/sesiones", true)
                .permitAll()
        );

        // logout
        http.logout(logout -> logout.permitAll());

        return http.build();
    }


//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//		// H2 console necesita iframes
//		http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//
//		http.authorizeHttpRequests(auth -> auth
//				// estáticos
//				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//
//				// público
//				.requestMatchers("/", "/saluda").permitAll()
//
//				// H2 solo ADMIN
//				.requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN").requestMatchers("/h2-console/**", "/h2/**")
//				.hasRole("ADMIN")
//
//				// productos: USER o ADMIN
//				.requestMatchers("/productos/**").hasAnyRole("USER", "ADMIN")
//
//				// el resto: autenticado
//				.anyRequest().authenticated());
//
//		// CSRF: desactivar solo para H2
//		http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console())
//				.ignoringRequestMatchers("/h2-console/**", "/h2/**"));
//
//		// login form
//		http.formLogin(form -> form.defaultSuccessUrl("/productos", true).permitAll());
//
//		http.logout(logout -> logout.permitAll());
//
//		return http.build();
//	}

}
