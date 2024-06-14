package com.springboot.backend.focusclubapp.focusclubbackend.models.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventoDTO {
    private Long idEvento;
    private String nombre;
    private Integer entradasDisponibles;
    private String descripcion;
    private Integer aforo;
    private LocalDate fecha;
    private BigDecimal precio;
    private String imagenUrl;
    private String nombreDiscoteca;

    // Getters y Setters
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getNombreDiscoteca() {
        return nombreDiscoteca;
    }

    public void setNombreDiscoteca(String nombreDiscoteca) {
        this.nombreDiscoteca = nombreDiscoteca;
    }
}
