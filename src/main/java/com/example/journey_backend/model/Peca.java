package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pecaId;

    private String tipo;
    private String tamanho;
    private String cor;
    private int quantidade;


    // Construtor vazio (obrigatório para JPA).
    public Peca() {}

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

    public String getTamanho() {return tamanho; }

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
