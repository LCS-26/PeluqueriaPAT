package com.pat.peluqueria.service;

import com.pat.peluqueria.entity.AppCita;
import com.pat.peluqueria.entity.AppUser;
import com.pat.peluqueria.model.ProfileRequest;
import com.pat.peluqueria.model.ProfileResponse;
import com.pat.peluqueria.model.RegisterRequest;

public interface CitaServiceInterface {

    ProfileResponse profile(AppCita appCita);

    ProfileResponse profile(AppCita appCita, ProfileRequest profile);

    ProfileResponse profile(RegisterRequest register);

    void delete(AppCita appCita);

}
