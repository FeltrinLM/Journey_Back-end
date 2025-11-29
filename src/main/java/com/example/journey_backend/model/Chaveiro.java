package com.example.journey_backend.model;

// 1. Imports do Jakarta Persistence (JPA)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// 2. Imports do Jakarta Validation
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity // Define a classe como uma entidade JPA.
@Table(name = "chaveiro")
public class Chaveiro {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int chaveiroId;

    @NotBlank
    private String chaveiroModelo;

    @ManyToOne // Vários chaveiros pertencem a uma coleção.
    @JoinColumn(name = "colecaoId") // Indica a foreign key.
    @NotNull
    private Colecao colecao;

    public Chaveiro() {
        // Construtor vazio (obrigatório para JPA).
    }

    // Construtor com parâmetros.
    public Chaveiro(String chaveiroModelo, Colecao colecao) {
        this.chaveiroModelo = chaveiroModelo;
        this.colecao = colecao;
    }

    // Getters e Setters
    public int getChaveiroId() {
        return chaveiroId;
    }

    public void setChaveiroId(int chaveiroId) {
        this.chaveiroId = chaveiroId;
    }

    public String getChaveiroModelo() {
        return chaveiroModelo;
    }

    public void setChaveiroModelo(String chaveiroModelo) {
        this.chaveiroModelo = chaveiroModelo;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }
}
