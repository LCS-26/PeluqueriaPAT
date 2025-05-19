package com.pat.peluqueria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReserva(

    @NotBlank
    Long
    @NotNull
    Dia dia,
){}
