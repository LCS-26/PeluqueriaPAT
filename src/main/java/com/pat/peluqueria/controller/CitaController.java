package com.pat.peluqueria.controller;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.entity.Token;
import com.pat.peluqueria.model.*;
import com.pat.peluqueria.repository.AppCitaRepository;
import com.pat.peluqueria.repository.AppUserRepository;
import com.pat.peluqueria.service.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CitaController {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AppCitaRepository appCitaRepository;

    @GetMapping("api/citas/peluquero/{id_peluquero}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AppCita>> getCitasPorPeluquero(@PathVariable("id_peluquero") Long peluqueroId, @CookieValue(value = "session", required = true) String session) {
        Optional<AppUser> peluquero = appUserRepository.findById(peluqueroId);
        if (peluquero.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<AppCita> citas = appCitaRepository.findByPeluquero(peluquero.get());
        if(citas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("/api/citas/cliente/{id_cliente}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AppCita>> getCitasPorCliente(@PathVariable("id_cliente") Long clienteId, @CookieValue(value = "session", required = true) String session) {
        Optional<AppUser> cliente = appUserRepository.findById(clienteId);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<AppCita> citas = appCitaRepository.findByCliente(cliente.get());
        if (citas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citas);
    }

    @GetMapping("api/citas/me/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AppCita> getCitaById(@PathVariable("id") Long id, @CookieValue(value = "session", required = true) String session) {
        Optional<AppCita> optionalCita = appCitaRepository.findById(id);

        if (optionalCita.isPresent()) {
            return ResponseEntity.ok(optionalCita.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/citas/me")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserva(@RequestBody RegisterReserva reserva) {
        Optional<AppUser> peluquero = appUserRepository.findById(reserva.peluqueroId());
        Optional<AppUser> cliente = appUserRepository.findById(reserva.clienteId());

        if (peluquero.isEmpty() || cliente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente o peluquero no encontrado");
        }

        AppCita cita = new AppCita();
        cita.setDia(Dia.valueOf(reserva.dia()));
        cita.setHora(reserva.hora());
        cita.setPeluquero(peluquero.get());
        cita.setCliente(cliente.get());

        appCitaRepository.save(cita);
    }


    //@PutMapping("api/citas/me")

    //@DeleteMapping("api/citas/me")

    @GetMapping("/api/citas/peluqueros")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AppUser>> getPeluqueros(@CookieValue(value = "session", required = true) String session) {
        List<AppUser> peluqueros = appUserRepository.findByRole(Role.PELUQUERO);
        return ResponseEntity.ok(peluqueros);
    }

}
