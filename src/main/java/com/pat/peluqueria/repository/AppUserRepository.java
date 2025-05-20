package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    List<AppUser> findByRole(Role role);
}