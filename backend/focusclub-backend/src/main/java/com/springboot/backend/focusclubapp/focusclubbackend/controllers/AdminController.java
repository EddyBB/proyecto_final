package com.springboot.backend.focusclubapp.focusclubbackend.controllers;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IEventoDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteUpdateDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.SalaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.*;
import com.springboot.backend.focusclubapp.focusclubbackend.models.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private IEventoDao eventoRepository;

    @Autowired
    private DiscotecaService discotecaService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private EntradaService entradaService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private RolService rolService;

    @GetMapping("/clientes")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Cliente> getAllClientes() {
        logger.debug("Obteniendo todos los clientes");
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        logger.debug("Obteniendo cliente con id: {}", id);
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        return optionalCliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/eventos")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Evento> getAllEventos() {
        logger.debug("Obteniendo todos los eventos");
        return eventoService.findAll();
    }

    @GetMapping("/eventos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        logger.debug("Obteniendo evento con id: {}", id);
        Optional<Evento> optionalEvento = eventoService.findById(id);
        return optionalEvento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/discotecas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Discoteca> getAllDiscotecas() {
        logger.debug("Obteniendo todas las discotecas");
        return discotecaService.findAll();
    }

    @GetMapping("/discotecas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Discoteca> getDiscotecaById(@PathVariable Long id) {
        logger.debug("Obteniendo discoteca con id: {}", id);
        Optional<Discoteca> optionalDiscoteca = discotecaService.findById(id);
        return optionalDiscoteca.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/salas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SalaDTO> getAllSalas() {
        logger.debug("Obteniendo todas las salas");
        return salaService.findAll();
    }

    @GetMapping("/salas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDTO> getSalaById(@PathVariable Long id) {
        logger.debug("Obteniendo sala con id: {}", id);
        Optional<SalaDTO> optionalSalaDTO = salaService.findById(id);
        return optionalSalaDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/entradas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Entrada> getAllEntradas() {
        logger.debug("Obteniendo todas las entradas");
        return entradaService.findAll();
    }

    @GetMapping("/entradas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Entrada> getEntradaById(@PathVariable Long id) {
        logger.debug("Obteniendo entrada con id: {}", id);
        Optional<Entrada> optionalEntrada = entradaService.findById(id);
        return optionalEntrada.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/compras")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CompraDTO> getAllCompras() {
        logger.debug("Obteniendo todas las compras");
        return compraService.findAll();
    }

    @GetMapping("/compras/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompraDTO> getCompraById(@PathVariable Long id) {
        logger.debug("Obteniendo compra con id: {}", id);
        Optional<CompraDTO> optionalCompra = compraService.findById(id);
        return optionalCompra.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rol> getAllRoles() {
        logger.debug("Obteniendo todos los roles");
        return rolService.findAll();
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        logger.debug("Obteniendo rol con id: {}", id);
        Optional<Rol> optionalRol = rolService.findById(id);
        return optionalRol.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/clientes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        logger.debug("Creando cliente: {}", cliente.getEmail());
        Cliente createdCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCliente);
    }

    @PostMapping("/eventos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) {
        logger.debug("Creando evento: {}", evento.getNombre());
        Evento createdEvento = eventoService.save(evento);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvento);
    }

    @PostMapping("/discotecas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Discoteca> createDiscoteca(@RequestBody Discoteca discoteca) {
        logger.debug("Creando discoteca: {}", discoteca.getNombre());
        Discoteca createdDiscoteca = discotecaService.save(discoteca);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscoteca);
    }

    @PostMapping("/salas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDTO> createSala(@RequestBody SalaDTO salaDTO) {
        logger.debug("Creando sala: {}", salaDTO.getNombre());
        SalaDTO createdSala = salaService.save(salaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSala);
    }

    @PostMapping("/entradas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Entrada> createEntrada(@RequestBody Entrada entrada) {
        logger.debug("Creando entrada para evento: {}", entrada.getEvento().getNombre());
        Entrada createdEntrada = entradaService.save(entrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntrada);
    }

    @PostMapping("/compras")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompraDTO> createCompra(@RequestBody CompraDTO compraDTO) {
        logger.debug("Creando compra");
        CompraDTO createdCompra = compraService.save(compraDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompra);
    }

    @PostMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        logger.debug("Creando rol: {}", rol.getTipoRol());
        Rol createdRol = rolService.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRol);
    }

    @PutMapping("/clientes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody ClienteUpdateDTO clienteDTO) {
        logger.debug("Actualizando cliente con id: {}", id);
        logger.debug("Datos del cliente recibidos para la actualización: {}", clienteDTO);
        Cliente updatedCliente = clienteService.updateCliente(id, clienteDTO);
        logger.debug("Cliente actualizado: {}", updatedCliente);
        return ResponseEntity.ok(updatedCliente);
    }
    

    @PutMapping("/eventos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Evento> updateEvento(@PathVariable Long id, @RequestBody Evento evento) {
        logger.debug("Actualizando evento con id: {}", id);
        Optional<Evento> optionalEvento = eventoService.findById(id);
        if (optionalEvento.isPresent()) {
            Evento existingEvento = optionalEvento.get();
            existingEvento.setNombre(evento.getNombre());
            existingEvento.setDescripcion(evento.getDescripcion());
            // Actualiza otros campos según sea necesario
            Evento updatedEvento = eventoService.save(existingEvento);
            return ResponseEntity.ok(updatedEvento);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/discotecas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Discoteca> updateDiscoteca(@PathVariable Long id, @RequestBody Discoteca discoteca) {
        logger.debug("Actualizando discoteca con id: {}", id);
        Optional<Discoteca> optionalDiscoteca = discotecaService.findById(id);
        if (optionalDiscoteca.isPresent()) {
            Discoteca existingDiscoteca = optionalDiscoteca.get();
            existingDiscoteca.setNombre(discoteca.getNombre());
            existingDiscoteca.setUbicacion(discoteca.getUbicacion());
            // Actualiza otros campos según sea necesario
            Discoteca updatedDiscoteca = discotecaService.save(existingDiscoteca);
            return ResponseEntity.ok(updatedDiscoteca);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/salas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDTO> updateSala(@PathVariable Long id, @RequestBody SalaDTO salaDTO) {
        logger.debug("Actualizando sala con id: {}", id);
        Optional<SalaDTO> optionalSalaDTO = salaService.findById(id);
        if (optionalSalaDTO.isPresent()) {
            SalaDTO existingSalaDTO = optionalSalaDTO.get();
            existingSalaDTO.setNombre(salaDTO.getNombre());
            // Actualiza otros campos según sea necesario
            SalaDTO updatedSalaDTO = salaService.save(existingSalaDTO);
            return ResponseEntity.ok(updatedSalaDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/entradas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Entrada> updateEntrada(@PathVariable Long id, @RequestBody Entrada entrada) {
        logger.debug("Actualizando entrada con id: {}", id);
        Optional<Entrada> optionalEntrada = entradaService.findById(id);
        if (optionalEntrada.isPresent()) {
            Entrada existingEntrada = optionalEntrada.get();
            existingEntrada.setEvento(entrada.getEvento());
            existingEntrada.setCliente(entrada.getCliente());
            existingEntrada.setFechaCompra(entrada.getFechaCompra());
            // Actualiza otros campos según sea necesario
            Entrada updatedEntrada = entradaService.save(existingEntrada);
            return ResponseEntity.ok(updatedEntrada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @PutMapping("/compras/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompraDTO> updateCompra(@PathVariable Long id, @RequestBody CompraDTO compraDTO) {
        logger.debug("Actualizando compra con id: {}", id);
        Optional<CompraDTO> optionalCompra = compraService.findById(id);
        if (optionalCompra.isPresent()) {
            CompraDTO existingCompraDTO = optionalCompra.get();
            existingCompraDTO.setClienteId(compraDTO.getClienteId());
            existingCompraDTO.setEventoId(compraDTO.getEventoId());
            existingCompraDTO.setCantidadEntradas(compraDTO.getCantidadEntradas());
            existingCompraDTO.setFechaCompra(compraDTO.getFechaCompra());
            
            // Calcular el nuevo precio por unidad y el precio total
            BigDecimal precioPorUnidad = eventoRepository.findById(compraDTO.getEventoId())
                    .map(Evento::getPrecio).orElse(BigDecimal.ZERO);
            BigDecimal precioTotal = precioPorUnidad.multiply(BigDecimal.valueOf(compraDTO.getCantidadEntradas()));
            existingCompraDTO.setPrecioEntrada(precioPorUnidad);
            existingCompraDTO.setPrecioTotal(precioTotal);
            
            CompraDTO updatedCompraDTO = compraService.save(existingCompraDTO);
            return ResponseEntity.ok(updatedCompraDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol rol) {
        logger.debug("Actualizando rol con id: {}", id);
        Optional<Rol> optionalRol = rolService.findById(id);
        if (optionalRol.isPresent()) {
            Rol existingRol = optionalRol.get();
            existingRol.setTipoRol(rol.getTipoRol());
            Rol updatedRol = rolService.save(existingRol);
            return ResponseEntity.ok(updatedRol);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    

    @DeleteMapping("/clientes/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        logger.debug("Eliminando cliente con id: {}", id);
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }    

    @DeleteMapping("/eventos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        logger.debug("Eliminando evento con id: {}", id);
        eventoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/discotecas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDiscoteca(@PathVariable Long id) {
        logger.debug("Eliminando discoteca con id: {}", id);
        discotecaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/salas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        logger.debug("Eliminando sala con id: {}", id);
        salaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/entradas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        logger.debug("Eliminando entrada con id: {}", id);
        entradaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/compras/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        logger.debug("Eliminando compra con id: {}", id);
        compraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        logger.debug("Eliminando rol con id: {}", id);
        rolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
