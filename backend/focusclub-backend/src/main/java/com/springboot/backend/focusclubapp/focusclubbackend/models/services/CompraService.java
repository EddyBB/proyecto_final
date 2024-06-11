package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import java.util.List;
import java.util.Optional;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;

public interface CompraService {
    List<CompraDTO> findByClienteId(Long clienteId);
    CompraDTO save(CompraDTO compraDTO);
    List<CompraDTO> findAll();
    void deleteById(Long id);
    Optional<CompraDTO> findById(Long id);
}

