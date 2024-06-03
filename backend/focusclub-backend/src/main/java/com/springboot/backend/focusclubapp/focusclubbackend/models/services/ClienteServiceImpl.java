package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IClienteDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IRolDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final IClienteDao clienteRepository;
    private final IRolDao rolRepository;

    @Autowired
    public ClienteServiceImpl(IClienteDao clienteRepository, IRolDao rolRepository) {
        this.clienteRepository = clienteRepository;
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        Rol rol = rolRepository.findById(cliente.getRol().getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol not found"));
        cliente.setRol(rol);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol getDefaultRol() {
        return rolRepository.findById(2L) // Suponiendo que el ID del rol "cliente" es 2
                .orElseThrow(() -> new RuntimeException("Default role not found"));
    }
}
