package com.pat.peluqueria.service;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.model.ProfileResponse;
import com.pat.peluqueria.repository.AppCitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaService implements CitaServiceInterface {

    @Autowired
    private AppCitaRepository appCitaRepository;

    @Override
    public ProfileResponse profile(AppCita appCita) {
        if (appCita == null) return null;

        return new ProfileResponse(
                appCita.getCliente().getUsername(),
                appCita.getCliente().getEmail(),
                appCita.getFecha().toString()
        );
    }

    @Override
    public ProfileResponse profile(AppCita appCita, com.pat.peluqueria.model.ProfileRequest profile) {
        return profile(appCita);
    }

    @Override
    public ProfileResponse profile(com.pat.peluqueria.model.RegisterRequest register) {
        return null;
    }

    @Override
    public void delete(AppCita appCita) {
        if (appCita != null && appCita.getId() != null) {
            appCitaRepository.deleteById(appCita.getId());
        }
    }
}
