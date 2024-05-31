package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;


public interface IClienteDao extends CrudRepository<Cliente, Long>{

    public Cliente findByNombrCliente(String nombre);

    @Query("select c from clientes u where c.nombre=?1")
    public Cliente findByNombreCliente2(String nombre);
}
