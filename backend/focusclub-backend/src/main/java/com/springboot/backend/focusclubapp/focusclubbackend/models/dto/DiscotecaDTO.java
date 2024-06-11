package com.springboot.backend.focusclubapp.focusclubbackend.models.dto;

public class DiscotecaDTO {
    private Long idDiscoteca;
    private String nombre;
    private String ubicacion;

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
