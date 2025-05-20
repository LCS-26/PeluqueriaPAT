package com.pat.peluqueria.controller;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.*;
import com.pat.peluqueria.repository.AppCitaRepository;
import com.pat.peluqueria.repository.AppUserRepository;
import com.pat.peluqueria.service.CitaService;
import com.pat.peluqueria.service.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("api/citas/peluquero/{id_peluquero}")
    public ResponseEntity<List<AppCita>> getCitasPorPeluquero(@PathVariable Long id_peluquero) {
        List<AppCita> citas = citaService.obtenerCitasPorPeluquero(id_peluquero);
        return citas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(citas);
    }

    @GetMapping("/api/citas/cliente/{id_cliente}")
    public ResponseEntity<List<AppCita>> getCitasPorCliente(@PathVariable Long id_cliente) {
        List<AppCita> citas = citaService.obtenerCitasPorCliente(id_cliente);
        return citas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(citas);
    }

    @GetMapping("api/citas/me/{id}")
    public ResponseEntity<AppCita> getCitaById(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/citas/me")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse reserva(@RequestBody RegisterReserva reserva) {
        return citaService.crearCita(reserva);
    }

    @PutMapping("/api/citas/me")
    public ResponseEntity<AppCita> modificarCita(@RequestBody ModificarReserva nuevaReserva) {
        AppCita modificada = citaService.modificarCita(nuevaReserva);
        return ResponseEntity.ok(modificada);
    }

    @DeleteMapping("/api/citas/me/{id_cita}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarCita(@PathVariable Long id_cita) {
        citaService.borrarCita(id_cita);
    }

    @GetMapping("/api/citas/peluqueros")
    public ResponseEntity<List<AppUser>> getPeluqueros() {
        return ResponseEntity.ok(citaService.obtenerPeluqueros());
    }

    @PreAuthorize("hasRole('ENCARGADO')")
    @GetMapping("api/citas/me/encargado")
    public List<AppCita> getCitas() {
        List<AppCita> citas = citaService.obtenerTodasLasCitas();
        if (citas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay citas programadas");
        }
        return citas;
    }
}