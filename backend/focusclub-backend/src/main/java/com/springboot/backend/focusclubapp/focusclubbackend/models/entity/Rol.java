package com.springboot.backend.focusclubapp.focusclubbackend.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

import jakarta.persistence.Column;

@Entity
@Table(name = "roles")
public class Rol implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    
    @Column(name = "tipo_rol", length = 50, nullable = false)
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

