package com.pat.peluqueria.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.CLIENTE, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmailFormat() {
        // Given: un email inválido
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "email-invalido",
                Role.CLIENTE, "aaaaaaA1");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);

        // Then
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testWeakPassword() {
        // Given: una contraseña que no cumple el patrón (sin mayúscula ni número)
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.CLIENTE, "password");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);

        // Then
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

}