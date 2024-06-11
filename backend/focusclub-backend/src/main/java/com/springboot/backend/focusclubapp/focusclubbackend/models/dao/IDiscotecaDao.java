package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Discoteca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiscotecaDao extends CrudRepository<Discoteca, Long> {
}
