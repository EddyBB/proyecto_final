package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IEntradaDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaServiceImpl implements EntradaService {

    @Autowired
    private IEntradaDao entradaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Entrada> findAll() {
        return (List<Entrada>) entradaDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entrada> findById(Long id) {
        return entradaDao.findById(id);
    }

    @Override
    @Transactional
    public Entrada save(Entrada entrada) {
        return entradaDao.save(entrada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        entradaDao.deleteById(id);
    }
}
