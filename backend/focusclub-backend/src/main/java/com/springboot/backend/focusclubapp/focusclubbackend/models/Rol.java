package com.springboot.backend.focusclubapp.focusclubbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    
    @Column(length = 50, nullable = false)
    private String tipoRol;

    // Constructor con argumentos
    public Rol(Long idRol, String tipoRol) {
        this.idRol = idRol;
        this.tipoRol = tipoRol;
    }

    // Constructor sin argumentos
    public Rol() {
    }

    // Getters and Setters
    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

    // toString
    @Override
    public String toString() {
        return "Rol{" +
                "idRol=" + idRol +
                ", tipoRol='" + tipoRol + '\'' +
                '}';
    }
}

