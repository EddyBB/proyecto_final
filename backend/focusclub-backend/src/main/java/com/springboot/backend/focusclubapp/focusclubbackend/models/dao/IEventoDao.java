package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventoDao extends CrudRepository<Evento, Long> {
}
