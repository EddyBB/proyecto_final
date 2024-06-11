package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.SalaDTO;

import java.util.List;
import java.util.Optional;

public interface SalaService {

    List<SalaDTO> findAll();

    Optional<SalaDTO> findById(Long id);

    SalaDTO save(SalaDTO salaDTO);

    void deleteById(Long id);
}
