package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ISalaDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.SalaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Sala;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaServiceImpl implements SalaService {

    private final ISalaDao salaRepository;

    @Autowired
    public SalaServiceImpl(ISalaDao salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaDTO> findAll() {
        List<Sala> salas = (List<Sala>) salaRepository.findAll();
        return salas.stream()
                .map(Mapper::toSalaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalaDTO> findById(Long id) {
        Optional<Sala> sala = salaRepository.findById(id);
        return sala.map(Mapper::toSalaDTO);
    }

    @Override
    @Transactional
    public SalaDTO save(SalaDTO salaDTO) {
        Sala sala = Mapper.toSala(salaDTO);
        Sala savedSala = salaRepository.save(sala);
        return Mapper.toSalaDTO(savedSala);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        salaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaDTO> findByEventoId(Long eventoId) {
        List<Sala> salas = salaRepository.findByEventoId(eventoId);
        return salas.stream()
                .map(Mapper::toSalaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalaDTO> findByDiscotecaId(Long discotecaId) {
        List<Sala> salas = salaRepository.findByDiscoteca_IdDiscoteca(discotecaId);
        return salas.stream()
                .map(Mapper::toSalaDTO)
                .collect(Collectors.toList());
    }
}

