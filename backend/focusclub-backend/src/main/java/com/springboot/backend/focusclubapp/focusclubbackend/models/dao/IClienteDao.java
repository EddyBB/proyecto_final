package com.springboot.backend.focusclubapp.focusclubbackend.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;


public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
