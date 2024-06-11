package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IDiscotecaDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ISalaDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Discoteca;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DiscotecaServiceImpl implements DiscotecaService {

    private final IDiscotecaDao discotecaRepository;
    private final ISalaDao salaRepository;

    @Autowired
    public DiscotecaServiceImpl(IDiscotecaDao discotecaRepository, ISalaDao salaRepository) {
        this.discotecaRepository = discotecaRepository;
        this.salaRepository = salaRepository;
    }

    @Override
    @Transactional
    public Discoteca save(Discoteca discoteca) {
        return discotecaRepository.save(discoteca);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Discoteca> findAll() {
        return (List<Discoteca>) discotecaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Discoteca> findById(Long id) {
        return discotecaRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Eliminar todas las salas relacionadas con la discoteca
        List<Sala> salas = salaRepository.findByDiscoteca_IdDiscoteca(id);
        for (Sala sala : salas) {
            salaRepository.delete(sala);
        }

        // Ahora eliminar la discoteca
        discotecaRepository.deleteById(id);
    }
}
