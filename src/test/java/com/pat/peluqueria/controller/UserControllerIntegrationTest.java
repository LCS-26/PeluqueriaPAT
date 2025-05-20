package com.pat.peluqueria.controller;

import com.pat.peluqueria.model.ProfileResponse;
import com.pat.peluqueria.model.RegisterRequest;
import com.pat.peluqueria.model.Role;
import com.pat.peluqueria.service.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    private static final long ID = 1;
    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";

    @Autowired private MockMvc mockMvc;

    @MockBean private UserServiceInterface userService;

    @Test void registerOk() throws Exception {
        Mockito.when(userService.profile(Mockito.any(RegisterRequest.class)))
                .thenReturn(new ProfileResponse(ID, NAME, EMAIL, Role.CLIENTE));

        String request = """
                {
                    "name": "%s",
                    "email": "%s",
                    "role": "%s",
                    "password": "aaaaaaA1"
                }
                """.formatted(NAME, EMAIL, Role.CLIENTE);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                        "id": %d,
                        "name": "%s",
                        "email": "%s",
                        "role": "%s"
                    }
                    """.formatted(ID, NAME, EMAIL, Role.CLIENTE)));
    }

    @Test void registerInvalidPassword() throws Exception {
        String request = """
                {
                    "name": "%s",
                    "email": "%s",
                    "role": "%s",
                    "password": "insegura"
                }
                """.formatted(NAME, EMAIL, Role.CLIENTE);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}