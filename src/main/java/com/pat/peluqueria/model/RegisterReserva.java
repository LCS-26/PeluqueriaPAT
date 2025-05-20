package com.pat.peluqueria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReserva(String dia, String hora, Long clienteId, Long peluqueroId) {}