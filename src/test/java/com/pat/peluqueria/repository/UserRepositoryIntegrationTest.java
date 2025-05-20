package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.entity.Token;
import com.pat.peluqueria.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    @Autowired AppCitaRepository appCitaRepository;

    @Test void saveTest() {
        // Given
        AppUser user = new AppUser();
        user.setEmail("test@email.com");
        user.setPassword("Test1234");
        user.setRole(Role.CLIENTE);
        user.setName("Test User");

        appUserRepository.save(user);

        Token token = new Token();
        token.setAppUser(user);
        tokenRepository.save(token);

        // When
        AppUser foundUser = appUserRepository.findByEmail("test@email.com").orElse(null);
        Token foundToken = tokenRepository.findByAppUser(user).orElse(null);

        // Then
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("test@email.com", foundUser.getEmail());

        Assertions.assertNotNull(foundToken);
        Assertions.assertEquals(user.getId(), foundToken.getAppUser().getId());
    }


    @Test
    public void deleteUserWithoutCitasTest() {
        // Crear y guardar un AppUser (cliente)
        AppUser cliente = new AppUser();
        cliente.setEmail("cliente@test.com");
        cliente.setName("Cliente Test");
        cliente.setPassword("1234");
        cliente.setRole(Role.CLIENTE);
        cliente = appUserRepository.save(cliente);

        // Comprobar que el usuario se ha guardado
        Assertions.assertTrue(appUserRepository.findById(cliente.getId()).isPresent());

        // Borrar el usuario
        appUserRepository.delete(cliente);

        // Comprobar que se ha borrado correctamente
        Assertions.assertFalse(appUserRepository.findById(cliente.getId()).isPresent());
    }
}