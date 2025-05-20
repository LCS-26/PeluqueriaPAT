package com.pat.peluqueria.model;

public record ProfileResponse(
    Long id,
    String name,
    String email,
    Role role
) { }
