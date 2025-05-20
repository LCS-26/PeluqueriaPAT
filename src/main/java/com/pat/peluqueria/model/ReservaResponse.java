package com.pat.peluqueria.model;

public record ReservaResponse(
        Long idCita,
        Long idCliente,
        String nombreCliente,
        Long idPeluquero,
        String nombrePeluquero,
        String dia,
        String hora
) {}
