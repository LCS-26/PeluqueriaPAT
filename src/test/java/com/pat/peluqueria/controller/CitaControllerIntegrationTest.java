package com.pat.peluqueria.controller;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.Dia;
import com.pat.peluqueria.model.ReservaResponse;
import com.pat.peluqueria.service.CitaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
class CitaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaService citaService;

    @WithMockUser(username = "encargado", roles = {"ENCARGADO"})
    @Test
    void createCitaOk() throws Exception {
        Long idCita = 1L;
        Long idCliente = 10L;
        Long idPeluquero = 20L;
        String dia = "LUNES";
        String hora = "10:00";

        // Simulación de la respuesta
        ReservaResponse reservaResponse = new ReservaResponse(
                idCita,
                idCliente,
                "Cliente Test",
                idPeluquero,
                "Peluquero Test",
                dia,
                hora
        );

        // Mock del servicio
        Mockito.when(citaService.crearCita(Mockito.any())).thenReturn(reservaResponse);

        // JSON de entrada
        String requestJson = """
        {
            "clienteId": %d,
            "peluqueroId": %d,
            "dia": "%s",
            "hora": "%s"
        }
        """.formatted(idCliente, idPeluquero, dia, hora);

        // Prueba del endpoint correcto: /api/citas/me
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/citas/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
            {
                "idCita": %d,
                "idCliente": %d,
                "nombreCliente": "Cliente Test",
                "idPeluquero": %d,
                "nombrePeluquero": "Peluquero Test",
                "dia": "%s",
                "hora": "%s"
            }
            """.formatted(idCita, idCliente, idPeluquero, dia, hora)));
    }

    @WithMockUser(username = "encargado", roles = {"ENCARGADO"})
    @Test
    void createCitaInvalidData() throws Exception {
        // Hora vacía (caso inválido)
        String requestJson = """
        {
            "clienteId": 10,
            "peluqueroId": 20,
            "dia": "LUNES",
            "hora": ""
        }
        """;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/citas/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}