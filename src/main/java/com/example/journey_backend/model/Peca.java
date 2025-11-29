package com.example.journey_backend.model;

// 1. Imports do Jakarta Persistence (JPA)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 2. Imports do Jakarta Validation
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "peca")
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pecaId;

    @NotBlank
    private String tipo;

    @NotBlank
    private String tamanho;

    @NotBlank
    private String cor;

    @Min(0)
    private int quantidade;

    public Peca() {
        // Construtor vazio (obrigatório para JPA).
    }

    // Construtor com parâmetros.
    public Peca(String tipo, String tamanho, String cor, int quantidade) {
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getPecaId() {
        return pecaId;
    }

    public void setPecaId(int pecaId) {
        this.pecaId = pecaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
