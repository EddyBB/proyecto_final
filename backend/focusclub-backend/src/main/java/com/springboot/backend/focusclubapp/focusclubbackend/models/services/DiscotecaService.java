package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Discoteca;
import java.util.List;
import java.util.Optional;

public interface DiscotecaService {

    List<Discoteca> findAll();

    Optional<Discoteca> findById(Long id);

    Discoteca save(Discoteca discoteca);

    void deleteById(Long id);
}
