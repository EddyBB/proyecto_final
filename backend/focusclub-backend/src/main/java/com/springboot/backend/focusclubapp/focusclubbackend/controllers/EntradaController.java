package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.EntradaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.ClienteService;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.EntradaService;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<EntradaDTO> list() {
        List<Entrada> entradas = entradaService.findAll();
        return entradas.stream()
                .map(Mapper::toEntradaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Entrada> entradaOptional = entradaService.findById(id);
        if (entradaOptional.isPresent()) {
            EntradaDTO entradaDTO = Mapper.toEntradaDTO(entradaOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(entradaDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "Entrada no encontrada por el id: " + id));
    }

    @PostMapping
    public ResponseEntity<EntradaDTO> create(@RequestBody EntradaDTO entradaDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Cliente> clienteOptional = clienteService.findByEmail(userDetails.getUsername());
        Optional<Evento> eventoOptional = eventoService.findById(entradaDTO.getEventoId());

        if (clienteOptional.isPresent() && eventoOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            Evento evento = eventoOptional.get();
            Entrada entrada = Mapper.toEntrada(entradaDTO, cliente, evento);
            entrada.setFechaCompra(LocalDateTime.now());

            Entrada entradaSaved = entradaService.save(entrada);
            EntradaDTO entradaSavedDTO = Mapper.toEntradaDTO(entradaSaved);
            return ResponseEntity.status(HttpStatus.CREATED).body(entradaSavedDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaDTO> update(@PathVariable Long id, @RequestBody EntradaDTO entradaDTO) {
        Optional<Entrada> entradaOptional = entradaService.findById(id);

        if (entradaOptional.isPresent()) {
            Entrada entradaDb = entradaOptional.get();
            entradaDb.setFechaCompra(entradaDTO.getFechaCompra());
            entradaDb.setPrecioTotal(entradaDTO.getPrecioTotal());
            entradaDb.setCantidadEntradas(entradaDTO.getCantidadEntradas());

            Entrada entradaUpdated = entradaService.save(entradaDb);
            EntradaDTO entradaUpdatedDTO = Mapper.toEntradaDTO(entradaUpdated);
            return ResponseEntity.ok(entradaUpdatedDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Entrada> entradaOptional = entradaService.findById(id);
        if (entradaOptional.isPresent()) {
            entradaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
