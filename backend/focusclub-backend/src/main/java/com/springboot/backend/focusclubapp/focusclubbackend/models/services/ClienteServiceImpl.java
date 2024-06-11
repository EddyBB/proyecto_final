package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IClienteDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IRolDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ICompraDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteUpdateDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final IClienteDao clienteRepository;
    private final IRolDao rolRepository;
    private final ICompraDao compraRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteServiceImpl(IClienteDao clienteRepository, IRolDao rolRepository, ICompraDao compraRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.rolRepository = rolRepository;
        this.compraRepository = compraRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        Rol rol = rolRepository.findById(cliente.getRol().getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        cliente.setRol(rol);
        if (!cliente.getPassword().startsWith("$2a$")) {
            // Solo codifica la contraseña si no está ya codificada
            String rawPassword = cliente.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            cliente.setPassword(encodedPassword);
        }
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
    @Transactional(readOnly = true)
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            // Elimina las compras relacionadas primero
            if (cliente.getCompras() != null) {
                cliente.getCompras().clear();
                compraRepository.deleteAll(cliente.getCompras());
            }
            clienteRepository.delete(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }
    

    @Override
    @Transactional(readOnly = true)
    public Rol getDefaultRol() {
        return rolRepository.findById(2L) // Suponiendo que el ID del rol "cliente" es 2
                .orElseThrow(() -> new RuntimeException("Default role no encontrado"));
    }

    @Override
    @Transactional
    public String saveImage(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String filePath = "path/to/images/directory/" + fileName; // Cambia esta ruta según sea necesario

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        return fileName;
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    @Transactional
    public Cliente updateCliente(Long id, ClienteUpdateDTO clienteDTO) {
        logger.debug("Buscando cliente con id: {}", id);
        Cliente existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    
        logger.debug("Cliente existente antes de la actualización: {}", existingCliente);
    
        existingCliente.setNombre(clienteDTO.getNombre());
        existingCliente.setApellidos(clienteDTO.getApellidos());
        existingCliente.setEmail(clienteDTO.getEmail());
        existingCliente.setTelefono(clienteDTO.getTelefono());
        existingCliente.setImagenPerfil(clienteDTO.getImagenPerfil());
    
        if (clienteDTO.getRolId() != null) {
            logger.debug("Buscando rol con id: {}", clienteDTO.getRolId());
            Rol rol = rolRepository.findById(clienteDTO.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            logger.debug("Rol encontrado: {}", rol);
            existingCliente.setRol(rol);
        } else {
            logger.debug("RolId no proporcionado, no se actualiza el rol");
        }
    
        if (!clienteDTO.getPassword().equals(existingCliente.getPassword())) {
            logger.debug("Codificando nueva contraseña para el cliente");
            existingCliente.setPassword(encodePassword(clienteDTO.getPassword()));
        } else {
            logger.debug("La contraseña no ha cambiado, no se actualiza");
        }
    
        Cliente savedCliente = clienteRepository.save(existingCliente);
        logger.debug("Cliente guardado después de la actualización: {}", savedCliente);
        
        return savedCliente;
    }
    
}
