package com.example.journey_backend.dto;

public class EstampaDTO {

    private int estampa_id;
    private String nome;
    private int quantidade;
    private int colecao_id;

    // Construtor vazio
    public EstampaDTO() {}

    // Construtor com parâmetros
    public EstampaDTO(int estampa_id, String nome, int quantidade, int colecao_id) {
        this.estampa_id = estampa_id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.colecao_id = colecao_id;
    }

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

    public int getColecao_id() {
        return colecao_id;
    }

    public void setColecao_id(int colecao_id) {
        this.colecao_id = colecao_id;
    }
}
