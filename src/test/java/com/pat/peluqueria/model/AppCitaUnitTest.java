package com.pat.peluqueria.model;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AppCitaUnitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        AppCita cita = new AppCita();
        cita.setCliente(new AppUser());  // No nulo
        cita.setPeluquero(new AppUser());  // No nulo
        cita.setDia(Dia.LUNES);  // Enum válido
        cita.setHora("10:30");  // No vacío

        Set<ConstraintViolation<AppCita>> violations = validator.validate(cita);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenClienteIsNull_thenViolation() {
        AppCita cita = new AppCita();
        cita.setCliente(null);
        cita.setPeluquero(new AppUser());
        cita.setDia(Dia.MARTES);
        cita.setHora("12:00");

        Set<ConstraintViolation<AppCita>> violations = validator.validate(cita);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cliente")));
    }

    @Test
    void whenPeluqueroIsNull_thenViolation() {
        AppCita cita = new AppCita();
        cita.setCliente(new AppUser());
        cita.setPeluquero(null);
        cita.setDia(Dia.MIERCOLES);
        cita.setHora("15:00");

        Set<ConstraintViolation<AppCita>> violations = validator.validate(cita);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("peluquero")));
    }

    @Test
    void whenDiaIsNull_thenViolation() {
        AppCita cita = new AppCita();
        cita.setCliente(new AppUser());
        cita.setPeluquero(new AppUser());
        cita.setDia(null);
        cita.setHora("09:00");

        Set<ConstraintViolation<AppCita>> violations = validator.validate(cita);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("dia")));
    }

    @Test
    void whenHoraIsBlank_thenViolation() {
        AppCita cita = new AppCita();
        cita.setCliente(new AppUser());
        cita.setPeluquero(new AppUser());
        cita.setDia(Dia.JUEVES);
        cita.setHora("");  // cadena vacía

        Set<ConstraintViolation<AppCita>> violations = validator.validate(cita);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("hora")));
    }
}
