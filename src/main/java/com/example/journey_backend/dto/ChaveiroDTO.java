package com.example.journey_backend.dto;

public class ChaveiroDTO {

    private int chaveiro_id;
    private String chaveiro_modelo;
    private int colecao_id;

    // Construtor vazio
    public ChaveiroDTO() {}

    // Construtor com parâmetros
    public ChaveiroDTO(int chaveiro_id, String chaveiro_modelo, int colecao_id) {
        this.chaveiro_id = chaveiro_id;
        this.chaveiro_modelo = chaveiro_modelo;
        this.colecao_id = colecao_id;
    }

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

    public int getColecao_id() {
        return colecao_id;
    }

    public void setColecao_id(int colecao_id) {
        this.colecao_id = colecao_id;
    }
}
