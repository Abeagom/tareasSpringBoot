package com.crud.configuracion;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.crud.modelo.Rol;

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
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // H2 console necesita iframes
        http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth -> auth
                // estáticos
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // H2 solo ADMIN
//              .requestMatchers(PathRequest.toH2Console()).hasRole(Rol.ADMIN.toString())
//              .requestMatchers("/h2-console/**", "/h2/**").hasRole(Rol.ADMIN.toString())
                // rutas según roles
                .requestMatchers("/sesiones/**").hasAnyRole(Rol.USUARIO.toString(), Rol.ADMIN.toString())
                .requestMatchers("/usuarios/**").hasAnyRole(Rol.MANAGER.toString(), Rol.ADMIN.toString())
                // el resto: autenticado
                .anyRequest().authenticated()
        );

        // login form
        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("nombre")
                .defaultSuccessUrl("/", true)
                .permitAll()
        );

        // logout (por defecto usa POST /logout)
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

//  @Bean
//  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//      // H2 console necesita iframes
//      http.headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//
//      http.authorizeHttpRequests(auth -> auth
//              // estáticos
//              .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//
//              // público
//              .requestMatchers("/", "/saluda").permitAll()
//
//              // H2 solo ADMIN
//              .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN").requestMatchers("/h2-console/**", "/h2/**")
//              .hasRole("ADMIN")
//
//              // productos: USER o ADMIN
//              .requestMatchers("/productos/**").hasAnyRole("USER", "ADMIN")
//
//              // el resto: autenticado
//              .anyRequest().authenticated());
//
//      // CSRF: desactivar solo para H2
//      http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console())
//              .ignoringRequestMatchers("/h2-console/**", "/h2/**"));
//
//      // login form
//      http.formLogin(form -> form.defaultSuccessUrl("/productos", true).permitAll());
//
//      http.logout(logout -> logout.permitAll());
//
//      return http.build();
//  }

}
