package com.pat.peluqueria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReserva(@NotBlank String dia,
                              @NotBlank String hora,
                              @NotNull Long clienteId,
                              @NotNull Long peluqueroId) {}