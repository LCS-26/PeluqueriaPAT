package com.pat.peluqueria.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           SessionAuthenticationFilter sessionFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Archivos públicos
                        .requestMatchers("/", "/index.html", "/styles.css", "/script.js", "/imagenes/**").permitAll()
                        // Páginas públicas
                        .requestMatchers("/login.html", "/registro.html").permitAll()
                        .requestMatchers("/login.js", "/registro.js").permitAll()
                        // Endpoints abiertos
                        .requestMatchers("/api/users", "/api/users/me/session").permitAll()
                        // Páginas protegidas por rol
                        .requestMatchers("/cliente.html").hasRole("CLIENTE")
                        .requestMatchers("/peluquero.html").hasRole("PELUQUERO")
                        .requestMatchers("/encargado.html").hasRole("ENCARGADO")
                        .anyRequest().authenticated()
                )
                // En lugar de redirigir a login, devuelve 401 (el frontend gestiona todo)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                )
                .addFilterBefore(sessionFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}