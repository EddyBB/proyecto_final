package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraService {
    List<Compra> findByClienteId(Long clienteId);

    Compra save(Compra compra);

    List<Compra> findAll();

    void deleteById(Long id);

    Optional<Compra> findById(Long id);
}
