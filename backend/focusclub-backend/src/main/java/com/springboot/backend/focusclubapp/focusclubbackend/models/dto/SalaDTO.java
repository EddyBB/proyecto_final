package com.springboot.backend.focusclubapp.focusclubbackend.models.dto;

public class SalaDTO {
    private Long idSala;
    private Long idDiscoteca;
    private Long idEvento;
    private String nombre;

    // Getters y Setters
    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public Long getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Long idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

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
}
