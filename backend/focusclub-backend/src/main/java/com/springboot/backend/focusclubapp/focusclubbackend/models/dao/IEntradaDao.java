package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntradaDao extends CrudRepository<Entrada, Long> {
}
