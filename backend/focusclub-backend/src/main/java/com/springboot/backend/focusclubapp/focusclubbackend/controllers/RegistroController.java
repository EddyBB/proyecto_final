package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/registro")
public class RegistroController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<ClienteDTO> register(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellidos(clienteDTO.getApellidos());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        String rawPassword = clienteDTO.getPassword();
        System.out.println("Raw Password: " + rawPassword);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
        cliente.setPassword(encodedPassword); // Codifica la contraseña
        cliente.setRol(clienteService.getDefaultRol());

        Cliente clienteSaved = clienteService.save(cliente);
        ClienteDTO clienteSavedDTO = Mapper.toClienteDTO(clienteSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSavedDTO);
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<Cliente> clienteOptional = clienteService.findById(id);
        if (clienteOptional.isPresent()) {
            String imageUrl = clienteService.saveImage(file);
            Cliente cliente = clienteOptional.get();
            cliente.setImagenPerfil(imageUrl);
            clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.OK).body("Imagen subida exitosamente: " + imageUrl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("El cliente no se encontró por el id: " + id);
    }
}
