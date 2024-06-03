package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin (origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    @Autowired
    public ClienteController(ClienteService service) {
        this.service = service;
    }

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
        cliente.setPassword(clienteDTO.getPassword());

        if (clienteDTO.getRol() == null) {
            cliente.setRol(service.getDefaultRol());
        } else {
            cliente.setRol(Mapper.toRol(clienteDTO.getRol()));
        }

        Cliente clienteSaved = service.save(cliente);
        ClienteDTO clienteSavedDTO = Mapper.toClienteDTO(clienteSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSavedDTO);
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
            clienteDb.setPassword(clienteDTO.getPassword());
            clienteDb.setRol(Mapper.toRol(clienteDTO.getRol()));

            Cliente clienteUpdated = service.save(clienteDb);
            ClienteDTO clienteUpdatedDTO = Mapper.toClienteDTO(clienteUpdated);
            return ResponseEntity.ok(clienteUpdatedDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = service.findById(id);
        if (clienteOptional.isPresent()) {
            try {
                service.deleteById(id);
                return ResponseEntity.noContent().build();
            } catch (DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("error", "Cannot delete or update a parent row: a foreign key constraint fails"));
            }
        }
        return ResponseEntity.notFound().build();
    }
}
