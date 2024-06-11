package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IEventoDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ISalaDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ICompraDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Sala;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImpl implements EventoService {

    private final IEventoDao eventoRepository;
    private final ISalaDao salaRepository;
    private final ICompraDao compraRepository;

    @Autowired
    public EventoServiceImpl(IEventoDao eventoRepository, ISalaDao salaRepository, ICompraDao compraRepository) {
        this.eventoRepository = eventoRepository;
        this.salaRepository = salaRepository;
        this.compraRepository = compraRepository;
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
        // Eliminar todas las compras que dependen del evento
        List<Compra> compras = compraRepository.findByEventoId(id);
        for (Compra compra : compras) {
            compraRepository.delete(compra);
        }
        
        // Eliminar todas las salas que dependen del evento
        List<Sala> salas = salaRepository.findByEventoId(id);
        for (Sala sala : salas) {
            salaRepository.delete(sala);
        }
        
        // Ahora podemos eliminar el evento
        eventoRepository.deleteById(id);
    }
}
