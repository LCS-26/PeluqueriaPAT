package com.pat.peluqueria;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SSR {

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/cliente")
    @PreAuthorize("hasRole('USER')")
    public String showClientePage() {
        return "cliente";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/encargado")
    public String showEncargadoPage() {
        return "encargado";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/peluquero")
    public String showPeluqueroPage() {
        return "peluquero";
    }
}