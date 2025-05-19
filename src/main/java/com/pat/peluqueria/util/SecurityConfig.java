package com.pat.peluqueria.util;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.pat.peluqueria.model.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/styles.css", "/script.js", "/imagenes/**").permitAll() // permite acceder sin login

                        // Rutas protegidas por rol
                        .requestMatchers("/encargado.html").hasRole("ENCARGADO")
                        .requestMatchers("/peluquero.html").hasRole("PELUQUERO")
                        .requestMatchers("/cliente.html").hasRole("CLIENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable) // opcional: desactiva formulario de login
                .httpBasic(AbstractHttpConfigurer::disable); // opcional: desactiva autenticación básica
        return http.build();
    }
}