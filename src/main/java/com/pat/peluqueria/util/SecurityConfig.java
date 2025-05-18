package com.pat.peluqueria.util;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/styles.css", "/script.js", "/imagenes/**").permitAll() // permite acceder sin login
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable) // opcional: desactiva formulario de login
                .httpBasic(AbstractHttpConfigurer::disable) // opcional: desactiva autenticación básica
                .csrf(AbstractHttpConfigurer::disable); // opcional: desactiva CSRF (solo si sabes lo que haces)

        return http.build();
    }
}