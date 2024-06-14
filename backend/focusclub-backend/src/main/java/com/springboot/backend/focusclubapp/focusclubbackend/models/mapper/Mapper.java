package com.springboot.backend.focusclubapp.focusclubbackend.models.mapper;

import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.ClienteDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.EntradaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.DiscotecaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.EventoDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.RolDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.SalaDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Cliente;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Entrada;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Discoteca;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Evento;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Rol;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Sala;

public class Mapper {

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdCliente(cliente.getIdCliente());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellidos(cliente.getApellidos());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setPassword(cliente.getPassword());
        clienteDTO.setImagenPerfil(cliente.getImagenPerfil());

        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(cliente.getRol().getIdRol());
        rolDTO.setTipoRol(cliente.getRol().getTipoRol());

        clienteDTO.setRol(rolDTO);
        return clienteDTO;
    }

    public static Cliente toCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(clienteDTO.getIdCliente());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellidos(clienteDTO.getApellidos());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setPassword(clienteDTO.getPassword());
        cliente.setImagenPerfil(clienteDTO.getImagenPerfil());

        Rol rol = new Rol();
        rol.setIdRol(clienteDTO.getRol().getIdRol());
        rol.setTipoRol(clienteDTO.getRol().getTipoRol());

        cliente.setRol(rol);
        return cliente;
    }

    public static EventoDTO toEventoDTO(Evento evento) {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setIdEvento(evento.getIdEvento());
        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setAforo(evento.getAforo());
        eventoDTO.setEntradasDisponibles(evento.getEntradasDisponibles());
        eventoDTO.setFecha(evento.getFecha());
        eventoDTO.setPrecio(evento.getPrecio());
        eventoDTO.setImagenUrl(evento.getImagenUrl());
        
        // Añadir el nombre de la discoteca si está disponible
        if (evento.getSalas() != null && !evento.getSalas().isEmpty()) {
            eventoDTO.setNombreDiscoteca(evento.getSalas().get(0).getDiscoteca().getNombre());
        }
        
        return eventoDTO;
    }

    public static Evento toEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setIdEvento(eventoDTO.getIdEvento());
        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setAforo(eventoDTO.getAforo());
        evento.setEntradasDisponibles(eventoDTO.getEntradasDisponibles());
        evento.setFecha(eventoDTO.getFecha());
        evento.setPrecio(eventoDTO.getPrecio());
        evento.setImagenUrl(eventoDTO.getImagenUrl());
        return evento;
    }

    public static DiscotecaDTO toDiscotecaDTO(Discoteca discoteca) {
        DiscotecaDTO discotecaDTO = new DiscotecaDTO();
        discotecaDTO.setIdDiscoteca(discoteca.getIdDiscoteca());
        discotecaDTO.setNombre(discoteca.getNombre());
        discotecaDTO.setUbicacion(discoteca.getUbicacion());
        return discotecaDTO;
    }

    public static Discoteca toDiscoteca(DiscotecaDTO discotecaDTO) {
        Discoteca discoteca = new Discoteca();
        discoteca.setIdDiscoteca(discotecaDTO.getIdDiscoteca());
        discoteca.setNombre(discotecaDTO.getNombre());
        discoteca.setUbicacion(discotecaDTO.getUbicacion());
        return discoteca;
    }

    public static EntradaDTO toEntradaDTO(Entrada entrada) {
        EntradaDTO entradaDTO = new EntradaDTO();
        entradaDTO.setIdEntrada(entrada.getIdEntrada());
        entradaDTO.setClienteId(entrada.getCliente().getIdCliente());
        entradaDTO.setEventoId(entrada.getEvento().getIdEvento());
        entradaDTO.setFechaCompra(entrada.getFechaCompra());
        entradaDTO.setPrecioTotal(entrada.getPrecioTotal());
        entradaDTO.setCantidadEntradas(entrada.getCantidadEntradas());
        return entradaDTO;
    }

    public static Entrada toEntrada(EntradaDTO entradaDTO, Cliente cliente, Evento evento) {
        Entrada entrada = new Entrada();
        entrada.setIdEntrada(entradaDTO.getIdEntrada());
        entrada.setCliente(cliente);
        entrada.setEvento(evento);
        entrada.setFechaCompra(entradaDTO.getFechaCompra());
        entrada.setPrecioTotal(entradaDTO.getPrecioTotal());
        entrada.setCantidadEntradas(entradaDTO.getCantidadEntradas());
        return entrada;
    }

    public static RolDTO toRolDTO(Rol rol) {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(rol.getIdRol());
        rolDTO.setTipoRol(rol.getTipoRol());
        return rolDTO;
    }

    public static Rol toRol(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setIdRol(rolDTO.getIdRol());
        rol.setTipoRol(rolDTO.getTipoRol());
        return rol;
    }

        public static SalaDTO toSalaDTO(Sala sala) {
        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setIdSala(sala.getIdSala());
        salaDTO.setIdDiscoteca(sala.getDiscoteca().getIdDiscoteca());
        salaDTO.setIdEvento(sala.getEvento().getIdEvento());
        salaDTO.setNombre(sala.getNombre());
        return salaDTO;
    }

    public static Sala toSala(SalaDTO salaDTO) {
        Sala sala = new Sala();
        sala.setIdSala(salaDTO.getIdSala());
        sala.setDiscoteca(new Discoteca(salaDTO.getIdDiscoteca()));
        sala.setEvento(new Evento(salaDTO.getIdEvento()));
        sala.setNombre(salaDTO.getNombre());
        return sala;
    }

    public static CompraDTO toCompraDTO(Compra compra) {
        CompraDTO dto = new CompraDTO();
        dto.setIdCompra(compra.getId());
        dto.setClienteId(compra.getCliente().getIdCliente());
        dto.setEventoId(compra.getEvento().getIdEvento());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setPrecioEntrada(compra.getPrecioEntrada());
        dto.setCantidadEntradas(compra.getCantidadEntradas());
        dto.setPrecioTotal(compra.getPrecioTotal());
        return dto;
    }
}
