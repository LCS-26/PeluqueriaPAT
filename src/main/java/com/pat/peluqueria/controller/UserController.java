package com.pat.peluqueria.controller;

import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.entity.Token;
import com.pat.peluqueria.model.*;
import com.pat.peluqueria.repository.AppUserRepository;
import com.pat.peluqueria.service.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;



@RestController
public class UserController {
    @Autowired
    UserServiceInterface userService;

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody RegisterRequest register) {
        try {
            return userService.profile(register);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/api/users/me/session")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest credentials) {
        Token token = userService.login(credentials.email(), credentials.password());
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ResponseCookie session = ResponseCookie
                .from("session", token.getId())
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .secure(false)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    @DeleteMapping("/api/users/me/session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "session", required = true) String session) {
        AppUser appUser = userService.authentication(session);
        if (appUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        userService.logout(session);
        ResponseCookie expireSession = ResponseCookie
                .from("session")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, expireSession.toString()).build();
    }

    @GetMapping("/api/users/me")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse profile(@CookieValue(value = "session", required = true) String session) {
        AppUser appUser = userService.authentication(session);
        if (appUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return userService.profile(appUser);
    }


    @PutMapping("/api/users/me")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse update(@RequestBody ProfileRequest profile, @CookieValue(value = "session", required = true) String session) {
        AppUser appUser = userService.authentication(session);
        if (appUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return userService.profile(appUser, profile);
    }

    @DeleteMapping("/api/users/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@CookieValue(value = "session", required = true) String session) {
        AppUser appUser = userService.authentication(session);
        if (appUser == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        userService.delete(appUser);
    }

    //CRUD para encargado
    @PreAuthorize("hasRole('ENCARGADO')")
    @GetMapping("api/users/me/encargado")
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileResponse> getClientes(@CookieValue(value = "session", required = true) String session) {
        return userService.getAllClientes();
    }

    //@PreAuthorize("hasRole('ENCARGADO')")
    //@PutMapping("api/users/me/email/{email}")
    @PreAuthorize("hasRole('ENCARGADO')")
    @PutMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse updateUsuarioPorId(@PathVariable Long id, @RequestBody ProfileRequest profile) {
        Optional<AppUser> usuarioOptional = appUserRepository.findById(id);

        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }

        AppUser cliente = usuarioOptional.get();
        return userService.profile(cliente, profile); // usa el mismo método de actualización
    }

}
