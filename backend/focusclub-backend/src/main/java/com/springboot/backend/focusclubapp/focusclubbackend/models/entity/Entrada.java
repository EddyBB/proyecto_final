package com.springboot.backend.focusclubapp.focusclubbackend.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "entradas")
public class Entrada implements Serializable{

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
    private LocalDate fechaCompra;

    @Column(nullable = false)
    private BigDecimal precioEntrada;

    // Constructor con argumentos
    public Entrada(Long idEntrada, Cliente cliente, Evento evento, LocalDate fechaCompra, BigDecimal precioEntrada) {
        this.idEntrada = idEntrada;
        this.cliente = cliente;
        this.evento = evento;
        this.fechaCompra = fechaCompra;
        this.precioEntrada = precioEntrada;
    }

    // Constructor sin argumentos
    public Entrada() {
    }

    // Getters and Setters
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

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(BigDecimal precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    // toString
    @Override
    public String toString() {
        return "Entrada{" +
                "idEntrada=" + idEntrada +
                ", cliente=" + cliente +
                ", evento=" + evento +
                ", fechaCompra=" + fechaCompra +
                ", precioEntrada=" + precioEntrada +
                '}';
    }
}

