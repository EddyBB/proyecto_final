package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteUpdateDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    Optional<Cliente> findByEmail(String email);

    Cliente save(Cliente cliente);

    void deleteById(Long id);

    Rol getDefaultRol();

    String saveImage(MultipartFile file);

    String encodePassword(String rawPassword);

    Cliente updateCliente(Long id, ClienteUpdateDTO clienteDTO);
}
