package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import java.util.List;
import java.util.Optional;

public interface EventoService {

    List<Evento> findAll();

    Optional<Evento> findById(Long id);

    Evento save(Evento evento);

    void deleteById(Long id);
}
