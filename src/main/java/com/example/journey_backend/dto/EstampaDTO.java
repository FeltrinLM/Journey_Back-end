package com.example.journey_backend.dto;

public class EstampaDTO {

    private int estampaId;
    private String nome;
    private int quantidade;
    private int colecaoId;

    // Construtor vazio
    public EstampaDTO() {}

    // Construtor com parâmetros
    public EstampaDTO(int estampaId, String nome, int quantidade, int colecaoId) {
        this.estampaId = estampaId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.colecaoId = colecaoId;
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

    public int getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(int colecaoId) {
        this.colecaoId = colecaoId;
    }

}
