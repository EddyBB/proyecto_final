package com.springboot.backend.focusclubapp.focusclubbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer entradasDisponibles;

    @Column(length = 255, nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Integer aforo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private BigDecimal precio;

    // Constructor con argumentos
    public Evento(Long idEvento, String nombre, Integer entradasDisponibles, String descripcion, Integer aforo, LocalDate fecha, BigDecimal precio) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.entradasDisponibles = entradasDisponibles;
        this.descripcion = descripcion;
        this.aforo = aforo;
        this.fecha = fecha;
        this.precio = precio;
    }

    // Constructor sin argumentos
    public Evento() {
    }

    // Getters and Setters
    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEntradasDisponibles() {
        return entradasDisponibles;
    }

    public void setEntradasDisponibles(Integer entradasDisponibles) {
        this.entradasDisponibles = entradasDisponibles;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    // toString
    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", nombre='" + nombre + '\'' +
                ", entradasDisponibles=" + entradasDisponibles +
                ", descripcion='" + descripcion + '\'' +
                ", aforo=" + aforo +
                ", fecha=" + fecha +
                ", precio=" + precio +
                '}';
    }
}

