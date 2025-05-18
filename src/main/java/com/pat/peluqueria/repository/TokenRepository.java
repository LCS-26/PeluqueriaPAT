package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByAppUser(AppUser appUser);
}
