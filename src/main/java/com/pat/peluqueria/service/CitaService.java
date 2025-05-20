package com.pat.peluqueria.service;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.*;
import com.pat.peluqueria.repository.AppCitaRepository;
import com.pat.peluqueria.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements CitaServiceInterface {

    @Autowired
    private AppCitaRepository appCitaRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    public List<AppCita> obtenerCitasPorPeluquero(Long id) {
        return appUserRepository.findById(id)
                .map(appCitaRepository::findByPeluquero)
                .orElse(Collections.emptyList());
    }

    public List<AppCita> obtenerCitasPorCliente(Long id) {
        return appUserRepository.findById(id)
                .map(appCitaRepository::findByCliente)
                .orElse(Collections.emptyList());
    }

    public Optional<AppCita> obtenerCitaPorId(Long id) {
        return appCitaRepository.findById(id);
    }

    public ReservaResponse crearCita(RegisterReserva reserva) {
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

        AppCita saved = appCitaRepository.save(cita);
        return new ReservaResponse(saved.getId(), saved.getCliente().getId(),saved.getCliente().getUsername(),
                saved.getPeluquero().getId(), saved.getPeluquero().getUsername(), saved.getDia(), saved.getHora());
    }

    public AppCita modificarCita(ModificarReserva reserva) {
        AppCita cita = appCitaRepository.findById(reserva.idCita())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));

        cita.setDia(Dia.valueOf(reserva.dia()));
        cita.setHora(reserva.hora());
        return appCitaRepository.save(cita);
    }

    public void borrarCita(Long id) {
        if (!appCitaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }
        appCitaRepository.deleteById(id);
    }

    public List<AppUser> obtenerPeluqueros() {
        return appUserRepository.findByRole(Role.PELUQUERO);
    }

    public List<AppCita> obtenerTodasLasCitas() {
        return (List<AppCita>) appCitaRepository.findAll();
    }
}