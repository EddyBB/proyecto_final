package com.springboot.backend.focusclubapp.focusclubbackend.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "discotecas")
public class Discoteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiscoteca;
    private String nombre;
    private String ubicacion;

    // Constructor vacío
    public Discoteca() {
    }

    // Constructor con id
    public Discoteca(Long idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    // Getters y Setters
    public Long getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Long idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
