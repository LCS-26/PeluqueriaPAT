package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    List<Cita> findByUserIdOrPeluqueroId(Long userId, Long peluqueroId);

}