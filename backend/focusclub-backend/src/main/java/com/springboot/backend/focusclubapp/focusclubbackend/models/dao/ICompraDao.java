package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompraDao extends CrudRepository<Compra, Long> {
    List<Compra> findByCliente_IdCliente(Long clienteId);
}
