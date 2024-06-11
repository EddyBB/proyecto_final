package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteDao extends CrudRepository<Cliente, Long> {
    @EntityGraph(attributePaths = "rol")
    Optional<Cliente> findByEmail(String email);
}
