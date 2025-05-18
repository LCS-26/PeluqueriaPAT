package com.pat.peluqueria.model;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank
    String email,
    @NotBlank
    String password
) {}
