package com.springboot.backend.focusclubapp.focusclubbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Discoteca")
public class Discoteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiscoteca;

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 150, nullable = false)
    private String ubicacion;

    // Constructor con argumentos
    public Discoteca(Long idDiscoteca, String nombre, String ubicacion) {
        this.idDiscoteca = idDiscoteca;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    // Constructor sin argumentos
    public Discoteca() {
    }

    // Getters and Setters
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

    // toString
    @Override
    public String toString() {
        return "Discoteca{" +
                "idDiscoteca=" + idDiscoteca +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}

