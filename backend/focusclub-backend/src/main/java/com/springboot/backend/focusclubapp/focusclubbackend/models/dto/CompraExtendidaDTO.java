package com.springboot.backend.focusclubapp.focusclubbackend.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraExtendidaDTO {
    private Long idCompra;
    private Long clienteId;
    private String clienteNombre;
    private Long eventoId;
    private String eventoNombre;
    private LocalDateTime fechaCompra;
    private BigDecimal precioEntrada;
    private int cantidadEntradas;
    private BigDecimal precioTotal;

    public CompraExtendidaDTO(Long idCompra, Long clienteId, String clienteNombre, Long eventoId, String eventoNombre, LocalDateTime fechaCompra, BigDecimal precioEntrada, Integer cantidadEntradas, BigDecimal precioTotal) {
        this.idCompra = idCompra;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.eventoId = eventoId;
        this.eventoNombre = eventoNombre;
        this.fechaCompra = fechaCompra;
        this.precioEntrada = precioEntrada;
        this.cantidadEntradas = cantidadEntradas;
        this.precioTotal = precioTotal;
    }

    // Getters and Setters

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(BigDecimal precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }
}
