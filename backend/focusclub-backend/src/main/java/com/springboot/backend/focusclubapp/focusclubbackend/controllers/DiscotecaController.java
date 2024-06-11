package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.DiscotecaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Discoteca;
import com.springboot.backend.focusclubapp.focusclubbackend.models.mapper.Mapper;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.DiscotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/discotecas")
public class DiscotecaController {

    @Autowired
    private DiscotecaService discotecaService;

    @GetMapping
    public List<DiscotecaDTO> list() {
        List<Discoteca> discotecas = discotecaService.findAll();
        return discotecas.stream()
                .map(Mapper::toDiscotecaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<Discoteca> discotecaOptional = discotecaService.findById(id);
        if (discotecaOptional.isPresent()) {
            DiscotecaDTO discotecaDTO = Mapper.toDiscotecaDTO(discotecaOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body(discotecaDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "La discoteca no se encontró por el id: " + id));
    }

    @PostMapping
    public ResponseEntity<DiscotecaDTO> create(@RequestBody DiscotecaDTO discotecaDTO) {
        Discoteca discoteca = Mapper.toDiscoteca(discotecaDTO);
        Discoteca discotecaSaved = discotecaService.save(discoteca);
        DiscotecaDTO discotecaSavedDTO = Mapper.toDiscotecaDTO(discotecaSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(discotecaSavedDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscotecaDTO> update(@PathVariable Long id, @RequestBody DiscotecaDTO discotecaDTO) {
        Optional<Discoteca> discotecaOptional = discotecaService.findById(id);

        if (discotecaOptional.isPresent()) {
            Discoteca discotecaDb = discotecaOptional.get();
            discotecaDb.setNombre(discotecaDTO.getNombre());
            discotecaDb.setUbicacion(discotecaDTO.getUbicacion());

            Discoteca discotecaUpdated = discotecaService.save(discotecaDb);
            DiscotecaDTO discotecaUpdatedDTO = Mapper.toDiscotecaDTO(discotecaUpdated);
            return ResponseEntity.ok(discotecaUpdatedDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Discoteca> discotecaOptional = discotecaService.findById(id);
        if (discotecaOptional.isPresent()) {
            discotecaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
