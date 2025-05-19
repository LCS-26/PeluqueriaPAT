package com.pat.peluqueria.repository;

import com.pat.peluqueria.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import com.pat.peluqueria.entity.AppCita;

import java.util.List;
import java.util.Optional;

public interface AppCitaRepository extends CrudRepository {
    List<AppCita> findByCliente(AppUser cliente);
    List<AppCita> findByPeluquero(AppUser peluquero);

}
