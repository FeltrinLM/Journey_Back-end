package com.example.journey_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity // Define a classe como uma entidade JPA.
@Table(name = "estampa")
public class Estampa {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int estampaId;

    @NotBlank
    private String nome;

    @Min(0)
    private int quantidade;

    @ManyToOne // Várias estampas pertencem a uma coleção.
    @JoinColumn(name = "colecaoId") // Indica a foreign key.
    @NotNull
    private Colecao colecao;

    // Construtor vazio (obrigatório para JPA).
    public Estampa() {}

    // Construtor com parâmetros.
    public Estampa(String nome, int quantidade, Colecao colecao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.colecao = colecao;
    }

    // Getters e Setters
    public int getEstampaId() {
        return estampaId;
    }

    public void setEstampaId(int estampaId) {
        this.estampaId = estampaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }
}
