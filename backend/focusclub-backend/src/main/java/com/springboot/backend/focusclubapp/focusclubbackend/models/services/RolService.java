package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import java.util.Optional;

public interface RolService {
    Optional<Rol> findById(Long id);
}