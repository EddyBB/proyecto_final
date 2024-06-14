package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.EventoDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<EventoDTO> list(@RequestParam(value = "date", required = false) String dateStr) {
        List<Evento> eventos;
        if (dateStr != null) {
            LocalDate date = LocalDate.parse(dateStr).atStartOfDay(ZoneId.of("UTC")).toLocalDate();
            System.out.println("Received Date: " + date); // Añadido para depuración
            eventos = eventoService.findByDate(date);
        } else {
            eventos = eventoService.findAll();
        }
        return eventos.stream()
                .map(Mapper::toEventoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Evento> eventoOptional = eventoService.findById(id);
        if (eventoOptional.isPresent()) {
            EventoDTO eventoDTO = Mapper.toEventoDTO(eventoOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(eventoDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "El evento no se encontró por el id: " + id));
    }

    @PostMapping
    public ResponseEntity<EventoDTO> create(@RequestBody EventoDTO eventoDTO) {
        Evento evento = Mapper.toEvento(eventoDTO);
        Evento eventoSaved = eventoService.save(evento);
        EventoDTO eventoSavedDTO = Mapper.toEventoDTO(eventoSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoSavedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> update(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Optional<Evento> eventoOptional = eventoService.findById(id);

        if (eventoOptional.isPresent()) {
            Evento eventoDb = eventoOptional.get();
            eventoDb.setNombre(eventoDTO.getNombre());
            eventoDb.setDescripcion(eventoDTO.getDescripcion());
            eventoDb.setFecha(eventoDTO.getFecha());
            eventoDb.setPrecio(eventoDTO.getPrecio());
            eventoDb.setAforo(eventoDTO.getAforo());
            eventoDb.setEntradasDisponibles(eventoDTO.getEntradasDisponibles());
            eventoDb.setImagenUrl(eventoDTO.getImagenUrl());

            Evento eventoUpdated = eventoService.save(eventoDb);
            EventoDTO eventoUpdatedDTO = Mapper.toEventoDTO(eventoUpdated);
            return ResponseEntity.ok(eventoUpdatedDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Evento> eventoOptional = eventoService.findById(id);
        if (eventoOptional.isPresent()) {
            eventoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
