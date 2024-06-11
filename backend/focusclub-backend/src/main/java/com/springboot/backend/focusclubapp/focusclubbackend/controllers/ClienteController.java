package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<ClienteDTO> list() {
        List<Cliente> clientes = service.findAll();
        return clientes.stream()
                .map(Mapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            ClienteDTO clienteDTO = Mapper.toClienteDTO(clienteOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "El usuario no se encontró por el id: " + id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellidos(clienteDTO.getApellidos());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword())); // Encriptar la contraseña

        Rol defaultRol = service.getDefaultRol();
        cliente.setRol(defaultRol);

        Cliente clienteSaved = service.save(cliente);
        ClienteDTO clienteSavedDTO = Mapper.toClienteDTO(clienteSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSavedDTO);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            String imageUrl = service.saveImage(file);
            Cliente cliente = clienteOptional.get();
            cliente.setImagenPerfil(imageUrl);
            service.save(cliente);
            return ResponseEntity.status(HttpStatus.OK).body("Imagen subida exitosamente: " + imageUrl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El cliente no se encontró por el id: " + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOptional = service.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente clienteDb = clienteOptional.get();
            clienteDb.setNombre(clienteDTO.getNombre());
            clienteDb.setApellidos(clienteDTO.getApellidos());
            clienteDb.setEmail(clienteDTO.getEmail());
            clienteDb.setTelefono(clienteDTO.getTelefono());
            clienteDb.setPassword(passwordEncoder.encode(clienteDTO.getPassword())); // Encriptar la contraseña
            clienteDb.setRol(service.getDefaultRol());
            clienteDb.setImagenPerfil(clienteDTO.getImagenPerfil()); // Asegúrate de incluir este campo en ClienteDTO

            Cliente clienteUpdated = service.save(clienteDb);
            ClienteDTO clienteUpdatedDTO = Mapper.toClienteDTO(clienteUpdated);
            return ResponseEntity.ok(clienteUpdatedDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
