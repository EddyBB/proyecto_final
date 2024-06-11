package com.springboot.backend.focusclubapp.focusclubbackend.models.services;


import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IEventoDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {

    private final IEventoDao eventoRepository;

    @Autowired
    public EventoServiceImpl(IEventoDao eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    @Transactional
    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evento> findAll() {
        return (List<Evento>) eventoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }
}
