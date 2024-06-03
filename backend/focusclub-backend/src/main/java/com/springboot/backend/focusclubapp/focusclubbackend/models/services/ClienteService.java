package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> findAll();

    Optional<Cliente> findById(@NonNull Long id);

    Cliente save(Cliente cliente);

    void deleteById(Long id);

    Rol getDefaultRol();
}
