package com.example.journey_backend.model;

// 1. Imports do Jakarta Persistence (JPA)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 2. Imports do Jakarta Validation
import jakarta.validation.constraints.NotBlank;

@Entity // Define a classe como uma entidade JPA.
@Table(name = "adesivo")
public class Adesivo {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int adesivoId;

    @NotBlank
    private String adesivoModelo;

    private boolean cromatico;

    public Adesivo() {
        // Construtor vazio (obrigatório para JPA).
    }


    // Construtor com parâmetros.
    public Adesivo(String adesivoModelo, boolean cromatico) {
        this.adesivoModelo = adesivoModelo;
        this.cromatico = cromatico;
    }

    // Getters e Setters
    public int getAdesivoId() {
        return adesivoId;
    }

    public void setAdesivoId(int adesivoId) {
        this.adesivoId = adesivoId;
    }

    public String getAdesivoModelo() {
        return adesivoModelo;
    }

    public void setAdesivoModelo(String adesivoModelo) {
        this.adesivoModelo = adesivoModelo;
    }

    public boolean isCromatico() {
        return cromatico;
    }

    public void setCromatico(boolean cromatico) {
        this.cromatico = cromatico;
    }
}
