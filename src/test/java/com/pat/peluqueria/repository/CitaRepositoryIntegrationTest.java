package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.Dia;
import com.pat.peluqueria.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class CitaRepositoryIntegrationTest {

    @Autowired
    private AppCitaRepository appCitaRepository;

    @Autowired
    private AppUserRepository appUserRepository;  // Para crear usuarios de prueba

    @Test
    void saveAndFindById() {
        // Crear cliente y peluquero para asignar a la cita
        AppUser cliente = new AppUser();
        cliente.setName("Cliente Test");
        cliente.setEmail("cliente@test.com");
        cliente.setPassword("1234");
        cliente.setRole(Role.CLIENTE);
        cliente = appUserRepository.save(cliente);

        AppUser peluquero = new AppUser();
        peluquero.setName("Peluquero Test");
        peluquero.setEmail("peluquero@test.com");
        peluquero.setPassword("1234");
        peluquero.setRole(Role.PELUQUERO);
        peluquero = appUserRepository.save(peluquero);

        // Crear cita
        AppCita cita = new AppCita();
        cita.setCliente(cliente);
        cita.setPeluquero(peluquero);
        cita.setDia(Dia.LUNES);
        cita.setHora("10:00");

        // Guardar cita
        cita = appCitaRepository.save(cita);

        // Recuperar cita guardada por ID
        Optional<AppCita> found = appCitaRepository.findById(cita.getId());

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("10:00", found.get().getHora());
        Assertions.assertEquals(Dia.LUNES.toString(), found.get().getDia());
        Assertions.assertEquals(cliente.getId(), found.get().getCliente().getId());
        Assertions.assertEquals(peluquero.getId(), found.get().getPeluquero().getId());
    }

    @Test
    void deleteCitaTest() {
        // Crear cliente y peluquero
        AppUser cliente = new AppUser();
        cliente.setName("Cliente Test 2");
        cliente.setEmail("cliente2@test.com");
        cliente.setPassword("1234");
        cliente.setRole(Role.CLIENTE);
        cliente = appUserRepository.save(cliente);

        AppUser peluquero = new AppUser();
        peluquero.setName("Peluquero Test 2");
        peluquero.setEmail("peluquero2@test.com");
        peluquero.setPassword("1234");
        peluquero.setRole(Role.PELUQUERO);
        peluquero = appUserRepository.save(peluquero);

        // Crear cita
        AppCita cita = new AppCita();
        cita.setCliente(cliente);
        cita.setPeluquero(peluquero);
        cita.setDia(Dia.MARTES);
        cita.setHora("15:00");

        cita = appCitaRepository.save(cita);

        // Borrar la cita
        appCitaRepository.delete(cita);

        // Comprobar que ya no existe
        Optional<AppCita> found = appCitaRepository.findById(cita.getId());
        Assertions.assertFalse(found.isPresent());
    }
}
