package com.springboot.backend.focusclubapp.focusclubbackend.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "entradas")
public class Entrada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private BigDecimal precioTotal;

    @Column(nullable = false)
    private int cantidadEntradas;

    // Constructor sin argumentos
    public Entrada() {
    }

    // Constructor con argumentos
    public Entrada(Long idEntrada, Cliente cliente, Evento evento, LocalDateTime fechaCompra, BigDecimal precioTotal, int cantidadEntradas) {
        this.idEntrada = idEntrada;
        this.cliente = cliente;
        this.evento = evento;
        this.fechaCompra = fechaCompra;
        this.precioTotal = precioTotal;
        this.cantidadEntradas = cantidadEntradas;
    }

    // Getters y Setters
    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getCantidadEntradas() {
        return cantidadEntradas;
    }

    public void setCantidadEntradas(int cantidadEntradas) {
        this.cantidadEntradas = cantidadEntradas;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "idEntrada=" + idEntrada +
                ", cliente=" + cliente +
                ", evento=" + evento +
                ", fechaCompra=" + fechaCompra +
                ", precioTotal=" + precioTotal +
                ", cantidadEntradas=" + cantidadEntradas +
                '}';
    }
}