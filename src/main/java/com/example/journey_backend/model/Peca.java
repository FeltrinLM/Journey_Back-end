package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity
public class Peca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int peca_id;

    private String tipo;
    private String tamanho;
    private String cor;
    private int quantidade;

    // Getters e Setters

    public int getPeca_id() {
        return peca_id;
    }

    public void setPeca_id(int peca_id) {
        this.peca_id = peca_id;
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
