package com.pat.peluqueria;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SSR {

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/cliente")
    public String showClientePage() {
        return "cliente";
    }

    @GetMapping("/encargado")
    public String showEncargadoPage() {
        return "encargado";
    }

    @GetMapping("/peluquero")
    public String showPeluqueroPage() {
        return "peluquero";
    }
}