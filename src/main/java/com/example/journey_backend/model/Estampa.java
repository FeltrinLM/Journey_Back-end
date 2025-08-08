package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity     //define a classe como uma entidade JPA.
public class Estampa {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //auto-incremento (estratégia do banco).
    private int estampa_id;

    private String nome;
    private int quantidade;

    @ManyToOne                        //define que várias estampas pertencem a uma coleção.
    @JoinColumn(name = "colecao_id") //indica a foreign key.
    private Colecao colecao;

    // Getters e Setters

    public int getEstampa_id() {
        return estampa_id;
    }

    public void setEstampa_id(int estampa_id) {
        this.estampa_id = estampa_id;
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
