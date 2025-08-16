package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity     //define a classe como uma entidade JPA.
public class Chaveiro {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremento (estratégia do banco).
    private int chaveiroId;

    private String chaveiroModelo;

    @ManyToOne                        //define que vários chaveiros pertencem a uma coleção.
    @JoinColumn(name = "colecaoId") //indica a foreign key.
    private Colecao colecao;

    // Construtor vazio (obrigatório para JPA).
    public Chaveiro() {}

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
