package com.springboot.backend.focusclubapp.focusclubbackend.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IClienteDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.ICompraDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dao.IEventoDao;
import com.springboot.backend.focusclubapp.focusclubbackend.models.dto.CompraDTO;
import com.springboot.backend.focusclubapp.focusclubbackend.models.entity.Compra;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private ICompraDao compraRepository;

    @Autowired
    private IClienteDao clienteRepository;

    @Autowired
    private IEventoDao eventoRepository;

    @Override
    public List<CompraDTO> findByClienteId(Long clienteId) {
        return compraRepository.findByCliente_IdCliente(clienteId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CompraDTO save(CompraDTO compraDTO) {
        Compra compra = convertToEntity(compraDTO);
        if (compra.getEvento() != null) {
            compra.setPrecioEntrada(compra.getEvento().getPrecio());
            compra.setPrecioTotal(compra.getPrecioEntrada().multiply(BigDecimal.valueOf(compra.getCantidadEntradas())));
        }
        Compra savedCompra = compraRepository.save(compra);
        return convertToDTO(savedCompra);
    }
    

    @Override
    public List<CompraDTO> findAll() {
        return StreamSupport.stream(compraRepository.findAll().spliterator(), false)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        compraRepository.deleteById(id);
    }

    @Override
    public Optional<CompraDTO> findById(Long id) {
        return compraRepository.findById(id).map(this::convertToDTO);
    }

    private CompraDTO convertToDTO(Compra compra) {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setIdCompra(compra.getId());
        compraDTO.setClienteId(compra.getCliente().getIdCliente());
        compraDTO.setEventoId(compra.getEvento().getIdEvento());
        compraDTO.setFechaCompra(compra.getFechaCompra());
        compraDTO.setPrecioEntrada(compra.getPrecioEntrada());
        compraDTO.setCantidadEntradas(compra.getCantidadEntradas());
        compraDTO.setPrecioTotal(compra.getPrecioTotal());
        return compraDTO;
    }
    
    private Compra convertToEntity(CompraDTO compraDTO) {
        Compra compra = new Compra();
        compra.setId(compraDTO.getIdCompra());
        compra.setCliente(clienteRepository.findById(compraDTO.getClienteId()).orElse(null));
        compra.setEvento(eventoRepository.findById(compraDTO.getEventoId()).orElse(null));
        compra.setFechaCompra(compraDTO.getFechaCompra());
        compra.setCantidadEntradas(compraDTO.getCantidadEntradas());
        compra.setPrecioEntrada(compraDTO.getPrecioEntrada());
        compra.setPrecioTotal(compraDTO.getPrecioTotal());
        return compra;
    }
    
}
