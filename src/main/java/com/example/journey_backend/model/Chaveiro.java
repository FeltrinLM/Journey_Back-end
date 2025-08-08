package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity     //define a classe como uma entidade JPA.
public class Chaveiro {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremento (estratégia do banco).
    private int chaveiro_id;

    private String chaveiro_modelo;

    @ManyToOne                        //define que vários chaveiros pertencem a uma coleção.
    @JoinColumn(name = "colecao_id") //indica a foreign key.
    private Colecao colecao;

    // Getters e Setters

    public int getChaveiro_id() {
        return chaveiro_id;
    }

    public void setChaveiro_id(int chaveiro_id) {
        this.chaveiro_id = chaveiro_id;
    }

    public String getChaveiro_modelo() {
        return chaveiro_modelo;
    }

    public void setChaveiro_modelo(String chaveiro_modelo) {
        this.chaveiro_modelo = chaveiro_modelo;
    }

    public Colecao getColecao() {
        return colecao;
    }

    public void setColecao(Colecao colecao) {
        this.colecao = colecao;
    }
}
