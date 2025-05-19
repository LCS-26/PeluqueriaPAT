package com.pat.peluqueria.model;

import com.pat.peluqueria.entity.AppUser;
import java.time.LocalDateTime;

public record CitaResponse(
    AppUser cliente,
    AppUser peluquero,
    String dia,
    String hora) {}
