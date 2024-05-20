package com.springboot.backend.focusclubapp.focusclubbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvento", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDiscoteca", nullable = false)
    private Discoteca discoteca;

    @Column(length = 100, nullable = false)
    private String nombre;

    // Constructor con argumentos
    public Sala(Long idSala, Evento evento, Discoteca discoteca, String nombre) {
        this.idSala = idSala;
        this.evento = evento;
        this.discoteca = discoteca;
        this.nombre = nombre;
    }

    // Constructor sin argumentos
    public Sala() {
    }

    // Getters and Setters
    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Discoteca getDiscoteca() {
        return discoteca;
    }

    public void setDiscoteca(Discoteca discoteca) {
        this.discoteca = discoteca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // toString
    @Override
    public String toString() {
        return "Sala{" +
                "idSala=" + idSala +
                ", evento=" + evento +
                ", discoteca=" + discoteca +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

