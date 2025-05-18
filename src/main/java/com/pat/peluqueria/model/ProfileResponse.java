package com.pat.peluqueria.model;

public record ProfileResponse(
    String name,
    String email,
    Role role
) { }
