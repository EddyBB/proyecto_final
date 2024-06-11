package com.springboot.backend.focusclubapp.focusclubbackend.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraDTO {

    private Long idCompra;
    private Long clienteId;
    private Long eventoId;
    private LocalDateTime fechaCompra;
    private BigDecimal precioEntrada;
    private int cantidadEntradas;
    private BigDecimal precioTotal;

    // Getters and setters

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
    public Long getEventoId() {
        return eventoId;
    }
    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
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
