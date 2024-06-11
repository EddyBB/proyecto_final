package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Sala;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalaDao extends CrudRepository<Sala, Long> {
    @Query("SELECT s FROM Sala s WHERE s.evento.idEvento = :eventoId")
    List<Sala> findByEventoId(@Param("eventoId") Long eventoId);
}
