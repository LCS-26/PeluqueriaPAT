package com.pat.peluqueria.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterReserva(

    @NotBlank
    Long id,
    @NotNull
    Dia dia
){}
