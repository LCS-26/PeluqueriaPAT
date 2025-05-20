package com.pat.peluqueria.service;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.*;

import java.util.List;
import java.util.Optional;

public interface CitaServiceInterface {

    // Métodos para gestión de citas
    List<AppCita> obtenerCitasPorPeluquero(Long idPeluquero);
    List<AppCita> obtenerCitasPorCliente(Long idCliente);
    Optional<AppCita> obtenerCitaPorId(Long id);
    ReservaResponse crearCita(RegisterReserva reserva);
    AppCita modificarCita(ModificarReserva reserva);    void borrarCita(Long idCita);
    List<AppUser> obtenerPeluqueros();
    List<AppCita> obtenerTodasLasCitas();
}
