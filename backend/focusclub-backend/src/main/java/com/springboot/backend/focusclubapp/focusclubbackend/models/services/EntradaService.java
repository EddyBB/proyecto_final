package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface EntradaService {

    List<Entrada> findAll();

    Optional<Entrada> findById(@NonNull Long id);

    Entrada save(Entrada entrada);

    void deleteById(Long id);
}
